# Hướng Dẫn Cấu Hình và Sử Dụng Resilience4j Trong Microservices

Tài liệu này cung cấp hướng dẫn chi tiết về cách cấu hình, thiết lập và sử dụng Resilience4j trong dự án **Base-Codebase**, tập trung vào hai cơ chế chính: **Circuit Breaker (Bộ ngắt mạch)** và **Rate Limiter (Bộ hạn chế tần suất)**.

---

## 1. Giới thiệu chung về Resilience4j

Trong kiến trúc Microservices, các dịch vụ thường xuyên giao tiếp với nhau qua mạng (REST, gRPC, v.v.). Khi một dịch vụ downstream gặp sự cố (bị sập, phản hồi cực kỳ chậm do quá tải hoặc lỗi cơ sở dữ liệu), dịch vụ upstream gọi tới nó có nguy cơ bị tắc nghẽn luồng xử lý (thread starvation), dẫn đến hiệu ứng đổ vỡ dây chuyền (cascading failure) toàn hệ thống.

**Resilience4j** là một thư viện gọn nhẹ, dễ sử dụng được thiết kế cho Java 8+ và phát triển dựa trên mô thức Functional Programming (lập trình hàm). Nó giúp xây dựng các hệ thống có khả năng chịu lỗi cao thông qua các mẫu thiết kế:

* **Circuit Breaker**: Tự động ngắt kết nối đến dịch vụ lỗi khi tỷ lệ lỗi vượt ngưỡng, cho phép hệ thống hồi phục.
* **Rate Limiter**: Hạn chế số lượng yêu cầu gọi đến một API trong một khoảng thời gian nhất định để tránh quá tải.
* **Retry**: Tự động thử lại cuộc gọi khi gặp lỗi tạm thời.
* **Bulkhead**: Giới hạn số lượng cuộc gọi đồng thời để bảo vệ tài nguyên luồng của ứng dụng.
* **Time Limiter**: Giới hạn thời gian xử lý tối đa của một tác vụ.

Trong dự án này, chúng ta sử dụng **Spring Cloud Circuit Breaker Resilience4j** giúp tích hợp mượt mà thông qua các cấu hình Declarative (khai báo YAML) và các Annotation trực quan.

---

## 2. Tư Duy Sử Dụng & Phân Tích Nghiệp Vụ Áp Dụng

Mỗi tính năng của Resilience4j được thiết kế cho các kịch bản nghiệp vụ cụ thể. Việc lạm dụng hoặc áp dụng sai chỗ sẽ làm tăng độ phức tạp của mã nguồn và có thể dẫn đến hành vi không mong muốn của hệ thống. Dưới đây là phân tích chi tiết khi nào nên áp dụng tính năng nào:

### 2.1. Khi nào sử dụng Circuit Breaker?

Circuit Breaker được sử dụng khi ứng dụng của bạn giao tiếp với một hệ thống khác qua mạng và hệ thống đó nằm ngoài tầm kiểm soát trực tiếp hoặc có nguy cơ bị lỗi/quá tải đột xuất.

**Các kịch bản nghiệp vụ nên dùng:**

1. **Gọi Dịch vụ Downstream (Internal Feign/gRPC Call):** Gọi giữa các microservices nội bộ. Ví dụ: `Order-Service` gọi `Inventory-Service` để giữ hàng. Nếu `Inventory-Service` bị sập hoặc nghẽn, `Order-Service` cần ngắt mạch để tránh việc nghẽn toàn bộ luồng tạo đơn hàng, đồng thời trả về một fallback phù hợp (báo hệ thống kho đang bận, thử lại sau).
2. **Tích hợp Cổng thanh toán hoặc Dịch vụ Thứ ba (Third-party APIs):** Các API như VnPay, Momo, Stripe, hoặc API gửi SMS, Sendgrid, v.v. Khi các cổng thanh toán này bị chậm hoặc lỗi liên tiếp (ví dụ trong dịp khuyến mãi lớn), Circuit Breaker giúp hệ thống của bạn không bị treo luồng xử lý và có thể tự động trả về thông báo bảo trì dịch vụ thanh toán cụ thể đó một cách nhanh chóng.
3. **Truy vấn Cơ sở dữ liệu phụ hoặc Cache ngoài tầm bảo vệ:** Khi truy vấn các nguồn dữ liệu có tính sẵn sàng không quá cao (như các dịch vụ ElasticSearch phức tạp, các cụm cơ sở dữ liệu báo cáo phụ). Nếu các truy vấn này bị chậm, ngắt mạch sẽ bảo vệ tài nguyên kết nối (HikariCP connection pool) cho luồng nghiệp vụ chính.

**Khi nào KHÔNG nên dùng:**

* Không dùng cho các hàm xử lý logic nội bộ (in-memory logic) hoặc tính toán thuần túy.
* Không dùng khi gọi các dịch vụ mà nếu lỗi thì toàn bộ luồng nghiệp vụ buộc phải dừng lại ngay lập tức và không thể có bất kỳ phương án dự phòng nào khác (trong trường hợp này, ném lỗi trực tiếp là giải pháp đúng đắn hơn là ngắt mạch).

---

### 2.2. Khi nào sử dụng Rate Limiter?

Rate Limiter được sử dụng để kiểm soát số lượng yêu cầu (requests) mà một khách hàng hoặc một luồng dịch vụ có thể thực hiện trong một khoảng thời gian nhằm bảo vệ tài nguyên hệ thống.

**Các kịch bản nghiệp vụ nên dùng:**

1. **API Gửi OTP / Gửi Email:** Ngăn chặn người dùng bấm gửi liên tục (spam) gây tốn chi phí SMS/Email hoặc gây quá tải hệ thống hàng đợi. Ví dụ: tối đa 1 số điện thoại chỉ được yêu cầu gửi OTP 1 lần trong 1 phút.
2. **API Tìm kiếm hoặc Tải file nặng:** Bảo vệ cơ sở dữ liệu khỏi các truy vấn tìm kiếm toàn văn (Full-text search) phức tạp hoặc tải báo cáo lớn. Hạn chế mỗi tài khoản chỉ được xuất báo cáo tối đa 5 lần mỗi phút.
3. **Public API hoặc API không có Authentication:** Bảo vệ các API công khai của hệ thống tránh bị các bot cào dữ liệu (crawlers) hoặc bị tấn công từ chối dịch vụ (DDOS) dạng nhẹ.
4. **Gọi API ngoài bị giới hạn hạn mức (Rate-limited Third-party APIs):** Một số API bên ngoài giới hạn số lượt gọi mỗi giây/phút dựa trên gói cước đăng ký (ví dụ: API thời tiết, API tỷ giá). Sử dụng Rate Limiter ở phía Client giúp ứng dụng tự động kiểm soát và xếp hàng các request thay vì nhận lỗi 429 liên tục từ phía đối tác.

---

### 2.3. Tư duy Thiết kế Fallback (Dự phòng)

Sự thành bại của mô hình Chống chịu lỗi phụ thuộc lớn vào chất lượng của hàm Fallback.

**Tư duy thiết kế Fallback theo nghiệp vụ:**

1. **Graceful Degradation (Suy giảm tính năng mềm dẻo):** Nếu dịch vụ lấy thông tin gợi ý sản phẩm bị lỗi, fallback có thể trả về một danh sách sản phẩm mặc định (bán chạy nhất) thay vì làm lỗi cả trang chủ.
2. **Static/Cached Data Fallback:** Trả về dữ liệu được lưu trong bộ nhớ đệm (Local cache / Redis cache) hoặc dữ liệu tĩnh có sẵn trong code.
3. **Fail-Silent (Lỗi im lặng):** Đối với các tác vụ không quan trọng như ghi log phân tích, gửi thông báo đẩy (push notification), fallback chỉ cần log lỗi lại và bỏ qua (trả về giá trị rỗng/mặc định) để tiến trình chính tiếp tục chạy bình thường.
4. **Fail-Over (Chuyển đổi dự phòng):** Nếu cổng thanh toán A bị ngắt mạch, fallback có thể chuyển hướng khách hàng sang sử dụng cổng thanh toán B.

---

## 3. Circuit Breaker: Cơ chế và Trạng thái

Circuit Breaker hoạt động tương tự như cầu chì trong mạng lưới điện gia đình. Nó theo dõi kết quả của các cuộc gọi gần nhất thông qua một cửa sổ trượt (Sliding Window) để quyết định xem có nên tiếp tục gửi yêu cầu đi hay không.

### 2.1. Các trạng thái hoạt động

Mạch điện của Circuit Breaker có 3 trạng thái chính:

1. **CLOSED (Đóng mạch)**:
    * Trạng thái bình thường. Mọi yêu cầu đều được gửi trực tiếp đến dịch vụ downstream.
    * Kết quả các cuộc gọi (thành công, thất bại, phản hồi chậm) được lưu lại trong bộ nhớ đệm (Sliding Window).
    * Nếu tỷ lệ lỗi hoặc tỷ lệ phản hồi chậm vượt ngưỡng thiết lập, mạch chuyển sang trạng thái **OPEN**.
2. **OPEN (Hở mạch / Ngắt mạch)**:
    * Dịch vụ downstream đang được coi là bị lỗi hoặc quá tải.
    * Mọi yêu cầu gọi đến dịch vụ này sẽ bị chặn ngay lập tức ở phía Client (không gửi qua mạng) và ném ra ngoại lệ `CallNotPermittedException`.
    * Một hàm **Fallback** (dự phòng) sẽ được gọi ngay lập tức để trả về kết quả mặc định hoặc thông báo lỗi thân thiện cho khách hàng.
    * Mạch sẽ giữ ở trạng thái OPEN trong một khoảng thời gian chờ định trước (`waitDurationInOpenState`), sau đó tự động chuyển sang trạng thái **HALF-OPEN**.
3. **HALF-OPEN (Mở một nửa)**:
    * Trạng thái thử nghiệm để kiểm tra xem dịch vụ downstream đã hồi phục hay chưa.
    * Một số lượng cuộc gọi giới hạn (`permittedNumberOfCallsInHalfOpenState`) sẽ được phép đi qua để đến dịch vụ downstream.
    * Nếu các cuộc gọi này thành công vượt trội và tỷ lệ lỗi thấp hơn ngưỡng, mạch chuyển lại về **CLOSED** (dịch vụ phục hồi thành công).
    * Nếu vẫn tiếp tục xảy ra lỗi hoặc phản hồi chậm vượt ngưỡng, mạch chuyển quay lại trạng thái **OPEN** và bắt đầu một chu kỳ chờ đợi mới.

### Sơ đồ chuyển đổi trạng thái

```text
       +------------------------- [Lỗi vượt ngưỡng] -------------------------+
       |                                                                     |
       v                                                                     |
  +---------+   ---[Chờ hết thời gian waitDurationInOpenState]--->   +-----------+
  |  OPEN   |                                                        | HALF-OPEN |
  +---------+   <----------[Tiếp tục lỗi / Thử nghiệm thất bại]-------   +-----------+
       ^                                                                     |
       |                                                                     |
       +------------------------- [Khôi phục thành công] <-------------------+
```

---

## 4. Cấu hình YAML Chi Tiết

Chúng ta khai báo các cấu hình mặc định (default) và các instance cụ thể trong file `application.yaml`. Dưới đây là giải thích chi tiết các tham số quan trọng:

```yaml
resilience4j:
  circuitbreaker:
    configs:
      default:
        # Loại cửa sổ trượt: COUNT_BASED (đếm số lần gọi) hoặc TIME_BASED (tính theo giây)
        slidingWindowType: COUNT_BASED
        # Kích thước cửa sổ trượt (số lượng cuộc gọi được lưu lại để tính toán tỷ lệ lỗi)
        slidingWindowSize: 10
        # Số lượng cuộc gọi tối thiểu cần thiết trước khi tính toán tỷ lệ lỗi
        minimumNumberOfCalls: 5
        # Tỷ lệ lỗi ngưỡng (%) mà khi vượt quá, mạch sẽ chuyển từ CLOSED sang OPEN
        failureRateThreshold: 50
        # Thời gian (mili giây) xác định một cuộc gọi bị coi là phản hồi chậm (Slow Call)
        slowCallDurationThreshold: 2000ms
        # Tỷ lệ phản hồi chậm ngưỡng (%) mà khi vượt quá, mạch chuyển sang OPEN
        slowCallRateThreshold: 50
        # Số lượng cuộc gọi thử nghiệm được phép đi qua ở trạng thái HALF-OPEN
        permittedNumberOfCallsInHalfOpenState: 3
        # Thời gian (mili giây) giữ mạch ở trạng thái OPEN trước khi chuyển sang HALF-OPEN
        waitDurationInOpenState: 5000ms
        # Tự động chuyển từ OPEN sang HALF-OPEN sau khi hết thời gian chờ
        automaticTransitionFromOpenToHalfOpenEnabled: true
    instances:
      # Định nghĩa một instance cụ thể kế thừa từ cấu hình mặc định
      backendServiceA:
        baseConfig: default
      backendServiceB:
        baseConfig: default
        slidingWindowSize: 20
        failureRateThreshold: 60

  ratelimiter:
    configs:
      default:
        # Số lượng yêu cầu tối đa được phép trong một chu kỳ làm mới
        limitForPeriod: 10
        # Thời gian của mỗi chu kỳ làm mới (Refresh Period)
        limitRefreshPeriod: 1s
        # Thời gian tối đa một thread phải chờ để có được token trước khi bị từ chối
        timeoutDuration: 0ms
    instances:
      publicApiRateLimiter:
        baseConfig: default
        limitForPeriod: 100
        limitRefreshPeriod: 1m
```

---

## 5. Hướng dẫn Lập trình với Annotation

Để áp dụng khả năng tự phục hồi vào code Java, chúng ta sử dụng các Annotation `@CircuitBreaker` và `@RateLimiter` được cung cấp bởi Resilience4j.

### 5.1. Quy tắc viết hàm Fallback (Bắt buộc)

Khi Circuit Breaker ngắt mạch hoặc Rate Limiter chặn cuộc gọi, hoặc khi dịch vụ downstream ném ra lỗi vật lý, Resilience4j sẽ tìm kiếm và thực thi hàm Fallback tương ứng.

Hàm Fallback phải tuân thủ nghiêm ngặt các quy tắc chữ ký hàm (Method Signature) sau:

1. **Vị trí**: Nằm trong cùng một Class với hàm gốc.
2. **Tên hàm**: Phải trùng khớp với giá trị khai báo ở tham số `fallbackMethod` của Annotation.
3. **Kiểu trả về (Return Type)**: Phải trùng khớp hoàn toàn với kiểu trả về của hàm gốc.
4. **Tham số truyền vào**:
    * Phải nhận đầy đủ tất cả các tham số của hàm gốc theo đúng thứ tự.
    * Phải thêm một tham số cuối cùng có kiểu dữ liệu là ngoại lệ `Throwable` (hoặc một Subclass cụ thể của ngoại lệ như `CallNotPermittedException`, `RequestNotPermittedException` để bắt riêng từng loại lỗi).

### 5.2. Ví dụ sử dụng `@CircuitBreaker`

Dưới đây là một Service gọi API lấy thông tin người dùng từ một dịch vụ bên ngoài sử dụng Feign Client hoặc RestTemplate:

```java
package com.common.service.impl;

import com.common.exception.ServiceUnavailableException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ExternalUserService {

    private final UserClient userClient;

    public ExternalUserService(UserClient userClient) {
        this.userClient = userClient;
    }

    /**
     * Lấy thông tin chi tiết người dùng từ dịch vụ ngoài.
     * Áp dụng Circuit Breaker tên là 'backendServiceA'.
     */
    @CircuitBreaker(name = "backendServiceA", fallbackMethod = "getUserDetailFallback")
    public UserDto getUserDetail(String userId) {
        log.info("Đang gọi dịch vụ ngoài để lấy thông tin user: {}", userId);
        return userClient.fetchUserDetail(userId);
    }

    /**
     * Hàm Fallback xử lý khi cuộc gọi gặp lỗi hoặc mạch đang hở (OPEN).
     */
    public UserDto getUserDetailFallback(String userId, Throwable throwable) {
        log.warn("Kích hoạt fallback cho user {}. Nguyên nhân lỗi: {}", userId, throwable.getMessage());
        
        // Kiểm tra loại exception để đưa ra phản hồi phù hợp
        if (throwable instanceof io.github.resilience4j.circuitbreaker.CallNotPermittedException) {
            log.warn("Mạch đang HỞ (OPEN). Block request ngay lập tức.");
        }

        // Trả về dữ liệu dự phòng (Graceful Degradation) thay vì làm sập ứng dụng
        return UserDto.builder()
                .id(userId)
                .username("Guest User")
                .email("guest@company.local")
                .isTemporary(true)
                .build();
    }
}
```

### 5.3. Ví dụ sử dụng `@RateLimiter`

Sử dụng `@RateLimiter` giúp bảo vệ các API quan trọng khỏi bị spam hoặc tấn công DDOS nhẹ, hoặc giới hạn tần suất gọi API bên ngoài nếu nhà cung cấp dịch vụ có chính sách giới hạn số lượt gọi.

```java
package com.common.service.impl;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OtpService {

    /**
     * Gửi mã OTP kích hoạt tài khoản.
     * Giới hạn tần suất gọi bằng rate limiter 'publicApiRateLimiter'.
     */
    @RateLimiter(name = "publicApiRateLimiter", fallbackMethod = "sendOtpFallback")
    public boolean sendOtp(String phoneNumber) {
        log.info("Đang gửi OTP đến số điện thoại: {}", phoneNumber);
        // Logic gửi tin nhắn SMS OTP thực tế
        return true;
    }

    /**
     * Hàm Fallback xử lý khi người dùng vượt quá số lượng request cho phép.
     */
    public boolean sendOtpFallback(String phoneNumber, io.github.resilience4j.ratelimiter.RequestNotPermittedException exception) {
        log.warn("Số điện thoại {} đã vượt quá tần suất gửi OTP cho phép: {}", phoneNumber, exception.getMessage());
        // Trả về false hoặc ném ra Custom Business Exception báo lỗi Too Many Requests (HTTP 429)
        throw new com.common.exception.TooManyRequestsException("Bạn đã yêu cầu gửi OTP quá nhanh. Vui lòng thử lại sau 1 phút.");
    }
}
```

---

## 6. Kết hợp nhiều Cơ chế (Chaining / Composition)

Chúng ta hoàn toàn có thể kết hợp nhiều cơ chế bảo vệ trên cùng một method. Khi đó, thứ tự thực thi của các Aspect (AOP) đóng vai trò quyết định.

Mặc định trong Spring, thứ tự ưu tiên (Aspect Order) của các thành phần trong Resilience4j được sắp xếp như sau (từ ngoài vào trong):
`Retry` -> `CircuitBreaker` -> `RateLimiter` -> `Bulkhead` -> `TimeLimiter`

Có nghĩa là cuộc gọi sẽ đi qua RateLimiter trước, sau đó mới đến CircuitBreaker bảo vệ, rồi mới thực hiện Retry nếu lỗi.

```java
@CircuitBreaker(name = "backendServiceA", fallbackMethod = "fallbackCombined")
@RateLimiter(name = "publicApiRateLimiter", fallbackMethod = "fallbackRateLimiter")
public DataDto processTransaction(TransactionRequest request) {
    return transactionClient.execute(request);
}

// Hàm fallback cho RateLimiter (bị chặn do tần suất)
public DataDto fallbackRateLimiter(TransactionRequest request, io.github.resilience4j.ratelimiter.RequestNotPermittedException e) {
    throw new TooManyRequestsException("Giao dịch bị từ chối do quá tần suất truy cập.");
}

// Hàm fallback cho CircuitBreaker (bị lỗi hệ thống hoặc ngắt mạch)
public DataDto fallbackCombined(TransactionRequest request, Throwable t) {
    log.error("Hệ thống giao dịch lỗi nghiêm trọng. Chuyển sang hàng đợi ngoại tuyến.");
    return DataDto.builder().status("PENDING_OFFLINE").build();
}
```

---

## 7. Kiểm thử và Giám sát (Monitoring)

### 6.1. Actuator Endpoints

Khi tích hợp `spring-boot-starter-actuator`, Resilience4j tự động đăng ký các chỉ số sức khỏe của Circuit Breaker vào endpoint `/actuator/health`.

Cấu hình để xem chi tiết thông tin trong `/actuator/health`:

```yaml
management:
  health:
    circuitbreakers:
      enabled: true
    ratelimiters:
      enabled: true
  endpoint:
    health:
      show-details: always
```

Khi gọi endpoint `/actuator/health`, bạn sẽ nhận được thông tin chi tiết về trạng thái của từng Circuit Breaker instance:

```json
{
  "status": "UP",
  "components": {
    "circuitBreakers": {
      "status": "UP",
      "details": {
        "backendServiceA": {
          "status": "UP",
          "details": {
            "failureRate": "-1.0%",
            "failureRateThreshold": "50.0%",
            "slowCallRate": "-1.0%",
            "slowCallRateThreshold": "50.0%",
            "bufferedCalls": 0,
            "failedCalls": 0,
            "slowCalls": 0,
            "slowFailedCalls": 0,
            "successfulCalls": 0,
            "state": "CLOSED"
          }
        }
      }
    }
  }
}
```

### 6.2. Prometheus Metrics

Thư viện xuất bản các chỉ số chi tiết giúp Prometheus thu thập và dựng dashboard trên Grafana:

* `resilience4j_circuitbreaker_state`: Trạng thái của circuit breaker (0 = closed, 1 = half-open, 2 = open).
* `resilience4j_circuitbreaker_buffered_calls`: Tổng số cuộc gọi được lưu trong Sliding Window.
* `resilience4j_circuitbreaker_calls_seconds`: Thời gian phản hồi và số lượng cuộc gọi thành công/thất bại.
* `resilience4j_ratelimiter_available_permissions`: Số lượng request còn được phép gọi tiếp theo trong chu kỳ hiện tại.

---

## 8. Các điểm lưu ý quan trọng khi vận hành

1. **Thread Safety**: Trạng thái của Circuit Breaker được quản lý hoàn toàn Thread-Safe thông qua các cấu trúc dữ liệu nguyên tử (Atomic/Concurrent) của Java.
2. **Lựa chọn Sliding Window**:
    * Sử dụng `COUNT_BASED` nếu lưu lượng truy cập hệ thống ổn định và dễ dự báo.
    * Sử dụng `TIME_BASED` nếu hệ thống có những khoảng thời gian cực kỳ vắng vẻ và những khoảng thời gian cao điểm, tránh việc các lỗi cũ từ lâu trong quá khứ làm ngắt mạch ở hiện tại.
3. **Hàm Fallback không được ném Exception không mong muốn**: Hãy chắc chắn rằng hàm fallback hoạt động cực kỳ tin cậy và không tự gây lỗi (trừ khi cố tình ném ra Business Exception định nghĩa trước). Nếu hàm fallback bị lỗi, khách hàng sẽ nhận lỗi 500 kèm stacktrace thô sơ, làm mất đi ý nghĩa bảo vệ của Circuit Breaker.
4. **Bỏ qua các Exception nghiệp vụ**: Không phải mọi lỗi đều nên làm ngắt mạch. Ví dụ, các exception do sai thông tin người dùng (như `UserNotFoundException`, `InvalidInputException` - mã lỗi HTTP 4xx) nên được cấu hình bỏ qua bằng tham số `ignoreExceptions`, chỉ tính các lỗi mạng hoặc lỗi hệ thống (HTTP 5xx, `TimeoutException`) vào tỷ lệ lỗi của Circuit Breaker:

    ```yaml
    resilience4j:
      circuitbreaker:
        configs:
          default:
            ignoreExceptions:
              - com.common.exception.UserNotFoundException
              - com.common.exception.ValidationException
    ```
