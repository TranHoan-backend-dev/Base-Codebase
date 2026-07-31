# Hướng Dẫn Cấu Hình Và Sử Dụng Redis Trong Hệ Thống Microservices

Tài liệu này cung cấp hướng dẫn chi tiết về cách cấu hình, hoạt động và các thực tiễn tốt nhất (best practices) khi sử dụng hệ thống cơ sở dữ liệu bộ nhớ đệm (caching) và lưu trữ phân tán **Redis** trong thư viện `Common`.

---

## 1. Tổng Quan Về Redis Trong Microservices

Trong kiến trúc hệ thống phân tán (Microservices), **Redis** đóng vai trò cực kỳ quan trọng và không chỉ giới hạn ở việc làm bộ nhớ đệm (Cache). Các ứng dụng thực tế thường áp dụng Redis cho các trường hợp sử dụng sau:

1. **Bộ nhớ đệm hiệu năng cao (Distributed Caching)**: Giảm tải cho cơ sở dữ liệu quan hệ (PostgreSQL, MySQL) bằng cách lưu trữ các truy vấn đọc nhiều, ít thay đổi.
2. **Khóa phân tán (Distributed Lock)**: Đảm bảo loại trừ tương hỗ (mutual exclusion) trên môi trường đa node chạy song song (sẽ được đặc tả chi tiết tại tài liệu `distributed-lock.md`).
3. **Hộp thư/Hàng đợi nhẹ (Message Broker)**: Sử dụng Redis Pub/Sub hoặc Redis Streams cho các nhu cầu giao tiếp thời gian thực đơn giản.
4. **Lưu trữ trạng thái phiên làm việc (Distributed Session)**: Đồng bộ thông tin đăng nhập hoặc giỏ hàng của người dùng trên toàn bộ cluster của hệ thống.

---

## 2. Sơ Đồ Kiến Trúc Hoạt Động (Architecture Flow)

Dưới đây là sơ đồ luồng xử lý dữ liệu khi sử dụng cơ chế Caching kết hợp với Database chính:

```text
                  +-----------------------------------+
                  |        Client Request             |
                  +-----------------+-----------------+
                                    |
                                    v
                  +-----------------+-----------------+
                  |      Spring @Cacheable Aspect     |
                  +-----------------+-----------------+
                                    |
                                    |--- (1) Kiểm tra Cache Key?
                                    v
                         /---------------------\
                        /   Có trong Redis?     \
                        \                       /
                         \---------------------/
                               /           \
                       CÓ (Hit)             KHÔNG (Miss)
                             /               \
                            v                 v
            +-----------------------+   +-----------------------+
            | Trả về dữ liệu ngay   |   | Truy vấn Database     |
            | từ bộ nhớ đệm Redis   |   | (PostgreSQL/MySQL)    |
            +-----------------------+   +-----------------------+
                                                  |
                                                  v
                                        +-----------------------+
                                        | Lưu kết quả vào Redis |
                                        | với thời gian TTL     |
                                        +-----------+-----------+
                                                    |
                                                    v
                                        +-----------------------+
                                        | Trả về cho Client     |
                                        +-----------------------+
```

---

## 3. Chiến Lược Cấu Hình & Kết Nối (Configuration & Lettuce Connection Pool)

Cấu hình Redis được đặt dưới khóa `spring.data.redis` trong file cấu hình [application.yaml](file:///d:/Du_an_ca_nhan/Base-Codebase/Common/src/main/resources/application.yaml). Để tối ưu hóa kết nối, hệ thống sử dụng thư viện **Lettuce** làm driver kết nối mặc định của Spring Boot.

### Cấu hình YAML Chi Tiết

```yaml
spring:
  data:
    redis:
      host: ${SPRING_REDIS_HOST:localhost}
      port: ${SPRING_REDIS_PORT:6379}
      password: ${SPRING_REDIS_PASSWORD:}
      timeout: 2000ms # Thời gian chờ kết nối tối đa
      lettuce:
        pool:
          max-active: 8    # Số lượng kết nối tối đa được phép hoạt động đồng thời
          max-idle: 8      # Số lượng kết nối nhàn rỗi tối đa được duy trì trong pool
          min-idle: 0      # Số lượng kết nối nhàn rỗi tối thiểu được duy trì
          max-wait: -1ms   # Thời gian tối đa chờ kết nối có sẵn từ pool (-1 là không giới hạn)
```

### lettuce Connection Pool là gì?

Khác với Jedis (sử dụng cơ chế chặn thread khi truy cập kết nối), **Lettuce** được xây dựng trên nền tảng bất đồng bộ và hướng sự kiện (dựa trên **Netty**). Nhờ vậy, nhiều thread có thể chia sẻ chung một kết nối vật lý duy nhất mà không bị khóa (thread-safe), giúp tăng đáng kể băng thông xử lý và giảm thiểu chi phí khởi tạo kết nối liên tục. Cấu hình Connection Pool giúp quản lý tài nguyên hiệu quả khi hệ thống chịu tải cực cao.

---

## 4. Chiến Lược Tuần Tự Hóa (Serialization Strategy)

Một trong những lỗi nghiêm trọng nhất của nhà phát triển khi mới tích hợp Redis vào Spring Boot là không thay đổi cơ chế tuần tự hóa mặc định.

### A. Vấn đề của Java Serializer mặc định (`JdkSerializationRedisSerializer`)

Mặc định, Spring Boot cấu hình `RedisTemplate` sử dụng serialization của JDK. Cơ chế này mang lại nhiều nhược điểm lớn:

* **Không thể đọc bằng mắt**: Dữ liệu lưu trong Redis sẽ ở dạng nhị phân thô, kèm theo các ký tự điều hướng tiền tố đặc trưng như `\xac\xed\x00\x05t\x00`. Việc kiểm tra dữ liệu thủ công qua Redis CLI gần như bất khả thi.
* **Rủi ro bảo mật nghiêm trọng**: Cơ chế Java Deserialization mở ra lỗ hổng tấn công thực thi mã từ xa (RCE) nếu hacker thay đổi payload nhị phân gửi lên.
* **Xung đột Class Version**: Nếu lớp Entity thay đổi thuộc tính hoặc `serialVersionUID` giữa các lần triển khai (deploy), quá trình đọc cache sẽ lập tức vỡ lỗi `SerializationException`.

### B. Giải pháp: Jackson JSON Serializer (`GenericJackson2JsonRedisSerializer`)

Để giải quyết triệt để các vấn đề trên, lớp [RedisConfiguration](file:///d:/Du_an_ca_nhan/Base-Codebase/Common/src/main/java/com/common/config/redis/RedisConfiguration.java) định nghĩa cấu hình ghi đè như sau:

* **Key**: Sử dụng `StringRedisSerializer` (Lưu dưới dạng chuỗi UTF-8 thuần túy, sạch sẽ).
* **Value**: Sử dụng `GenericJackson2JsonRedisSerializer`.

```java
// Đoạn mã cấu hình mẫu trong RedisConfiguration.java
StringRedisSerializer stringSerializer = new StringRedisSerializer();
GenericJackson2JsonRedisSerializer jsonSerializer = new GenericJackson2JsonRedisSerializer();

template.setKeySerializer(stringSerializer);
template.setValueSerializer(jsonSerializer);
```

#### Ưu điểm

1. **Dữ liệu dạng JSON rõ ràng**: Bạn có thể dễ dàng chạy lệnh `GET key` trong Redis CLI để kiểm tra dữ liệu dạng JSON thô.
2. **Linh hoạt cấu trúc**: Jackson tự động bỏ qua các trường không khớp nếu có sự thay đổi nhẹ trong DTO/Entity, tăng tính tương thích ngược.
3. **Tự động ánh xạ kiểu dữ liệu (Polymorphism support)**: Thư viện tuần tự hóa đính kèm thuộc tính `"@class"` trong JSON để giúp Spring tự động nhận biết kiểu dữ liệu của Class nguyên bản khi deserialize mà không cần ép kiểu thủ công.

---

## 5. Cơ Chế Caching Với Spring Cache (Annotation-based Caching)

Hệ thống cung cấp cơ chế Cache khai báo bằng Annotation thông qua `@EnableCaching`. Dưới đây là các Annotation cốt lõi và hướng dẫn sử dụng chi tiết:

### A. `@Cacheable`

Dùng ở các phương thức đọc dữ liệu. Spring sẽ kiểm tra trong Redis trước: nếu có (Hit), trả về ngay; nếu không (Miss), chạy logic hàm và lưu kết quả vào Redis.

```java
@Cacheable(value = "system_setting_cache", key = "#key")
public String getSettingValue(String key) {
    // Thực thi logic truy vấn Database nặng nề ở đây...
    return repository.findBySettingKey(key).getSettingValue();
}
```

### B. `@CachePut`

Dùng ở các phương thức cập nhật dữ liệu. Phương thức sẽ luôn được thực thi, đồng thời cập nhật đè giá trị mới vào Redis cache.

```java
@CachePut(value = "system_setting_cache", key = "#key")
public String updateSettingValue(String key, String newValue) {
    // Cập nhật database
    repository.updateValue(key, newValue);
    return newValue; // Giá trị trả về này sẽ được lưu đè vào cache
}
```

### C. `@CacheEvict`

Dùng ở các phương thức xóa dữ liệu. Tự động xóa bỏ một hoặc toàn bộ bản ghi cache tương ứng khỏi Redis để tránh dữ liệu rác/lỗi thời.

```java
@CacheEvict(value = "system_setting_cache", key = "#key")
public void deleteSetting(String key) {
    repository.deleteByKey(key);
}
```

* **Xóa toàn bộ cache trong group**: Sử dụng tham số `allEntries = true`.

    ```java
    @CacheEvict(value = "system_setting_cache", allEntries = true)
    public void clearAllCache() {
        // Xóa sạch toàn bộ các key thuộc namespace "system_setting_cache"
    }
    ```

---

## 6. Lập Trình Trực Tiếp Với RedisTemplate (Programmatic Caching)

Trong nhiều trường hợp nghiệp vụ phức tạp mà Annotation-based không đáp ứng được (như đặt thời gian hết hạn TTL động, thao tác với các cấu trúc dữ liệu đặc thù của Redis như Set, Hash, List, ZSet), nhà phát triển nên sử dụng `RedisTemplate` trực tiếp.

### Các ví dụ sử dụng cơ bản

```java
@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    // 1. Thao tác với chuỗi (String) kèm TTL động
    public void setValueWithExpiry(String key, Object value, Duration duration) {
        redisTemplate.opsForValue().set(key, value, duration);
    }

    public Object getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // 2. Thao tác với Hash (Thích hợp lưu trữ Object phẳng)
    public void putInHash(String hashKey, String field, Object value) {
        redisTemplate.opsForHash().put(hashKey, field, value);
    }

    public Object getFromHash(String hashKey, String field) {
        return redisTemplate.opsForHash().get(hashKey, field);
    }

    // 3. Thao tác với Set (Tập hợp duy nhất không trùng lặp)
    public void addToSet(String key, Object... values) {
        redisTemplate.opsForSet().add(key, values);
    }

    public boolean isMemberOfSet(String key, Object member) {
        Boolean result = redisTemplate.opsForSet().isMember(key, member);
        return result != null && result;
    }
}
```

---

## 7. Các Bài Toán Thực Tế Và Cách Khắc Phục (Common Patterns & Solutions)

Khi áp dụng Redis trên môi trường Production cấp độ cao, bạn phải luôn có giải pháp phòng tránh các hiện tượng thảm họa cache sau:

### A. Tuyết Lở Cache (Cache Avalanche)

* **Hiện tượng**: Hàng triệu key cache hết hạn (TTL) tại cùng một thời điểm. Mọi request đổ dồn trực tiếp xuống Database cùng lúc gây quá tải, dẫn đến sập toàn bộ hệ thống Database.
* **Giải pháp**:
    1. **Độ lệch thời gian ngẫu nhiên (Jitter/Random TTL)**: Khi lưu cache, hãy cộng thêm một khoảng thời gian ngẫu nhiên (ví dụ: từ 1 đến 5 phút) vào TTL mặc định.
    2. Triển khai thiết lập thời gian TTL khác nhau cho các phân lớp dữ liệu khác nhau.

### B. Mất Dấu Cache / Lọc Rác (Cache Penetration)

* **Hiện tượng**: Người dùng liên tục truy vấn các dữ liệu không hề tồn tại trong cả Cache lẫn Database (ví dụ: truy cập ID âm, ID rác tự sinh). Hệ thống sẽ luôn bị Cache Miss và phải xuống Database tìm kiếm vô ích.
* **Giải pháp**:
    1. **Lưu kết quả trống (Cache Null Values)**: Lưu kết quả trống hoặc null vào Redis với thời gian hết hạn cực ngắn (ví dụ: 1-2 phút) để chặn các request tiếp theo có cùng ID rác. (Trong cấu hình của chúng tôi, mặc định tắt lưu null nhằm tiết kiệm RAM, tuy nhiên có thể tùy biến lưu với các giá trị rác đặc thù).
    2. Sử dụng **Bloom Filter** ở lớp Gateway để sàng lọc các ID không hợp lệ trước khi đi vào dịch vụ.
    3. Thực hiện kiểm tra tính hợp lệ của tham số ngay tại lớp Controller (Validation).

### C. Nghẽn Điểm Hết Hạn (Cache Breakdown / Hotkey Expired)

* **Hiện tượng**: Một key được truy vấn với tần suất siêu cao (ví dụ: thông tin khuyến mãi của một sản phẩm cực hot đang chạy flash sale) bất ngờ hết hạn. Hàng vạn request song song sẽ cùng lúc thọc xuống Database để truy vấn và cập nhật lại cache.
* **Giải pháp**:
    1. Sử dụng Khóa phân tán (Distributed Lock) để đảm bảo tại một thời điểm chỉ có 1 request được quyền xuống Database lấy dữ liệu và cập nhật lại Cache, các request khác phải đợi và đọc lại từ Cache sau khi được cập nhật.
    2. Thiết lập cơ chế tự động gia hạn trước khi hết hạn (Background Refresh).

---

## 8. Các Lưu Ý Quan Trọng Khi Sử Dụng (Important Developer Cautions)

> [!WARNING]
>
> * **Tránh lưu đối tượng quá lớn (Big Keys)**: Một Key/Value có kích thước vượt quá vài MB sẽ gây nghẽn luồng xử lý đơn nhân của Redis. Hãy chia nhỏ dữ liệu hoặc chỉ lưu các trường thông tin cần thiết.
> * **Tên Namespace nhất quán**: Luôn đặt tên group cache rõ ràng theo định dạng `dự án:tính_năng:chìa_khóa` để tránh trùng lặp đè dữ liệu giữa các microservices dùng chung 1 cụm Redis.
