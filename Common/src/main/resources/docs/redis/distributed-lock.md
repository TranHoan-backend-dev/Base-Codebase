# Đặc tả kỹ thuật: Hệ thống Khóa Phân Tấn (Distributed Lock) với Redis & ShedLock

Tài liệu này cung cấp hướng dẫn chi tiết về cấu hình, nguyên lý hoạt động, cách triển khai và sử dụng hệ thống **Khóa Phân Tấn (Distributed Lock)** trong hệ sinh thái dự án `Base-Codebase`. Hệ thống hỗ trợ cả cơ chế khóa tự động cho tác vụ lập lịch (`ShedLock`) và cơ chế khóa lập trình thủ công (`Programmatic Lock`) bằng Lua Script an toàn.

---

## 1. Tổng quan về Khóa Phân Tấn (Distributed Lock)

### 1.1. Tại sao cần Khóa Phân Tấn?

Trong kiến trúc Monolith truyền thống, khi muốn đồng bộ hóa luồng (thread synchronization) để tránh tranh chấp tài nguyên (Race Condition), chúng ta thường dùng các từ khóa có sẵn của ngôn ngữ như `synchronized` hoặc các lớp khóa cục bộ như `ReentrantLock` trong Java.

Tuy nhiên, trong kiến trúc Microservices hiện đại, hệ thống được chia nhỏ và triển khai thành nhiều instance độc lập (multi-instance) chạy song song trên các môi trường container hóa (Docker, Kubernetes). Các instance này không chia sẻ chung bộ nhớ JVM. Do đó, các cơ chế khóa cục bộ JVM hoàn toàn mất tác dụng.

```text
┌──────────────────┐       ┌──────────────────┐
│   Instance 01    │       │   Instance 02    │
│  (Thread Pool)   │       │  (Thread Pool)   │
└────────┬─────────┘       └────────┬─────────┘
         │                          │
         │   Yêu cầu lock Key X     │
         ▼                          ▼
   ┌──────────────────────────────────────┐
   │         Hệ thống Redis chung         │
   │  (Lưu trạng thái lock & kiểm tra)    │
   └──────────────────────────────────────┘
```

Khóa phân tán là giải pháp đưa trạng thái khóa ra một **Storage dùng chung** (trong trường hợp này là **Redis**). Tất cả các instance trước khi thao tác lên tài nguyên chung đều phải hỏi và chiếm quyền khóa trên Storage này. Chỉ có instance chiếm được khóa thành công mới được phép thực thi tác vụ.

### 1.2. Các yêu cầu cốt lõi của Khóa Phân Tấn

Một hệ thống khóa phân tán tin cậy cần đảm bảo các yếu tố sau:

1. **Loại trừ tương hỗ (Mutual Exclusion):** Tại một thời điểm, chỉ có duy nhất một Client/Thread chiếm được khóa cho một tài nguyên nhất định.
2. **Tránh Deadlock (Deadlock Free):** Khóa phải có thời gian sống tự động (TTL - Time To Live). Nếu Client chiếm khóa bị crash hoặc mất kết nối mạng giữa chừng, khóa sẽ tự động hết hạn giải phóng để các Client khác không bị treo vĩnh viễn.
3. **Giải phóng an toàn (Safe Release):** Một Client không được phép giải phóng khóa của Client khác. Khi mở khóa, phải xác nhận chính xác định danh duy nhất (Token/UUID) của Client đang nắm giữ khóa đó.

---

## 2. Giải pháp 1: Khóa tác vụ lập lịch định kỳ với ShedLock

### 2.1. Vấn đề của `@Scheduled` trong Microservices

Spring cung cấp annotation `@Scheduled` giúp lập lịch chạy các tác vụ ngầm định kỳ cực kỳ tiện lợi. Tuy nhiên, khi deploy dự án thành 3 instance chạy song song, đến thời điểm trigger (ví dụ 0h sáng), cả 3 instance sẽ đồng loạt khởi chạy tác vụ đó. Điều này dẫn đến:

- Lãng phí tài nguyên hệ thống (CPU, RAM).
- Trùng lặp dữ liệu (ví dụ gửi email báo cáo 3 lần cho 1 khách hàng, insert trùng bản ghi lịch sử giao dịch).

### 2.2. Cơ chế hoạt động của ShedLock

ShedLock là thư viện chuyên dụng giúp giải quyết vấn đề trên bằng cách sử dụng một Storage dùng chung (ở đây là Redis) để theo dõi trạng thái thực thi của các Scheduled Job.

Khi một Scheduled Task được kích hoạt trên Instance A:

1. ShedLock sẽ cố gắng tạo một key tương ứng với Job đó trên Redis.
2. Nếu tạo thành công (chiếm được lock), Instance A sẽ chạy tác vụ ngầm.
3. Nếu tạo thất bại (tức là Instance B hoặc C đã chiếm lock trước đó), ShedLock sẽ bỏ qua lượt thực thi lần này trên Instance A.

### 2.3. Khai báo Dependency

Trong file [build.gradle.kts](file:///d:/Du_an_ca_nhan/Base-Codebase/Common/build.gradle.kts), chúng ta nạp thêm 2 thư viện sau:

```kotlin
implementation("net.javacrumbs.shedlock:shedlock-spring:5.13.0")
implementation("net.javacrumbs.shedlock:shedlock-provider-redis-spring:5.13.0")
```

### 2.4. Cấu hình ShedLock

Lớp cấu hình [ShedLockConfiguration.java](file:///d:/Du_an_ca_nhan/Base-Codebase/Common/src/main/java/com/common/config/redis/ShedLockConfiguration.java) kích hoạt cơ chế khóa ShedLock thông qua Redis:

```java
package com.common.config.redis;

import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.redis.spring.RedisLockProvider;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;

@Configuration
@EnableSchedulerLock(defaultLockAtMostFor = "10m")
public class ShedLockConfiguration {

    @Bean
    public LockProvider lockProvider(RedisConnectionFactory connectionFactory) {
        // Sử dụng RedisLockProvider để ShedLock quản lý các Key trạng thái trong Redis
        return new RedisLockProvider(connectionFactory);
    }
}
```

**Các tham số quan trọng:**

- `defaultLockAtMostFor`: Thời gian giữ khóa tối đa mặc định (ví dụ: `10m` - 10 phút) để phòng ngừa trường hợp node đang chạy job bị crash đột ngột. Nếu node bị sập, sau 10 phút khóa sẽ tự động mở.

### 2.5. Hướng dẫn sử dụng `@SchedulerLock`

Để áp dụng ShedLock cho một tác vụ, chúng ta gắn thêm `@SchedulerLock` bên cạnh `@Scheduled`:

```java
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ReportingScheduler {

    @Scheduled(cron = "0 0 0 * * ?") // Chạy vào lúc 0h00 hàng ngày
    @SchedulerLock(
        name = "generateDailyReportJob", 
        lockAtMostFor = "15m", 
        lockAtLeastFor = "5m"
    )
    public void generateDailyReport() {
        // Logic sinh báo cáo ngày
        System.out.println("Đang tạo báo cáo ngày...");
    }
}
```

**Chi tiết thuộc tính:**

- `name`: Tên định danh duy nhất của tác vụ. ShedLock sẽ dùng tên này làm key lưu trên Redis (thường được prefix dạng `job-lock:name`).
- `lockAtMostFor`: Thời gian giữ khóa tối đa cho tác vụ cụ thể này. Nó ghi đè `defaultLockAtMostFor`.
- `lockAtLeastFor`: Thời gian giữ khóa tối thiểu. Giúp bảo vệ tác vụ không bị chạy lại bởi các node khác khi thời gian lệch múi giờ (clock drift) giữa các server nhỏ hơn khoảng thời gian này, hoặc khi job thực thi quá nhanh (chỉ mất vài giây).

---

## 3. Giải pháp 2: Khóa lập trình thủ công (Programmatic Lock)

Có nhiều trường hợp nghiệp vụ yêu cầu chúng ta phải tự lock/unlock thủ công trong code (ví dụ: trừ tiền ví điện tử của user, cập nhật số lượng kho hàng khi đặt mua sản phẩm flash-sale).

### 3.1. Thiết kế an toàn với Lua Script

Một lỗi cực kỳ phổ biến khi tự triển khai Redis Lock lập trình đó là **Xóa nhầm khóa của Thread khác (Lock Release Race Condition)**:

1. **Thread A** acquire lock với Key `lock:user:100` thành công, TTL = 5 giây.
2. **Thread A** xử lý tác vụ nghiệp vụ quá nặng, tốn mất 7 giây.
3. Sau 5 giây, lock trên Redis tự động hết hạn và biến mất.
4. **Thread B** nhảy vào acquire lock Key `lock:user:100` thành công để thực thi tác vụ của nó.
5. Sau 7 giây, **Thread A** hoàn thành tác vụ nghiệp vụ, nhảy vào block `finally` gọi lệnh xóa khóa `DEL lock:user:100`.
6. Kết quả: **Thread A** vô tình xóa mất khóa mà **Thread B** đang nắm giữ! Dẫn đến một **Thread C** thứ ba có thể nhảy vào tranh chấp cùng Thread B.

#### Giải pháp khắc phục

Khi acquire lock, Client phải sinh ra một **Token định danh duy nhất (UUID/Value ngẫu nhiên)** để gán vào Value của Key lock đó. Khi mở khóa, Client phải thực hiện 2 bước:

1. `GET key` xem value hiện tại có trùng với Token mình đã truyền vào lúc acquire không.
2. Nếu trùng thì mới gọi `DEL key`.

Hai bước trên phải được thực thi **Atomic (nguyên tử)** trên Redis để tránh tranh chấp giữa thời điểm GET và DEL. Chúng ta sử dụng **Lua Script** chạy trực tiếp trên Redis Engine để hiện thực hóa việc này:

```lua
if redis.call('get', KEYS[1]) == ARGV[1] then
    return redis.call('del', KEYS[1])
else
    return 0
end
```

### 3.2. Đặc tả API của IDistributedLockService

Interface [IDistributedLockService.java](file:///d:/Du_an_ca_nhan/Base-Codebase/Common/src/main/java/com/common/service/contract/IDistributedLockService.java):

```java
package com.common.service.contract;

public interface IDistributedLockService {

    boolean tryLock(String lockKey, String lockValue, long expireTimeInMs);

    boolean tryLockWithTimeout(String lockKey, String lockValue, long expireTimeInMs, long timeoutInMs);

    boolean releaseLock(String lockKey, String lockValue);
}
```

### 3.3. Lớp triển khai RedisDistributedLockServiceImpl

Lớp triển khai [RedisDistributedLockServiceImpl.java](file:///d:/Du_an_ca_nhan/Base-Codebase/Common/src/main/java/com/common/service/impl/RedisDistributedLockServiceImpl.java) sử dụng `RedisTemplate` và Lua Script:

```java
package com.common.service.impl;

import com.common.service.contract.IDistributedLockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisDistributedLockServiceImpl implements IDistributedLockService {

    private final RedisTemplate<String, Object> redisTemplate;

    // Định nghĩa Lua Script giải phóng khóa nguyên tử
    private static final String RELEASE_LOCK_SCRIPT =
            "if redis.call('get', KEYS[1]) == ARGV[1] then " +
            "    return redis.call('del', KEYS[1]) " +
            "else " +
            "    return 0 " +
            "end";

    @Override
    public boolean tryLock(String lockKey, String lockValue, long expireTimeInMs) {
        try {
            log.debug("Cố gắng acquire lock: {}, với value: {}, TTL: {} ms", lockKey, lockValue, expireTimeInMs);
            // Sử dụng lệnh SETNX (SET IF NOT EXISTS) kèm TTL
            Boolean result = redisTemplate.opsForValue().setIfAbsent(lockKey, lockValue, Duration.ofMillis(expireTimeInMs));
            return Boolean.TRUE.equals(result);
        } catch (Exception e) {
            log.error("Lỗi khi cố gắng acquire lock: {}", lockKey, e);
            return false;
        }
    }

    @Override
    public boolean tryLockWithTimeout(String lockKey, String lockValue, long expireTimeInMs, long timeoutInMs) {
        long endTime = System.currentTimeMillis() + timeoutInMs;
        log.info("Cố gắng acquire lock kèm timeout: {}, value: {}, TTL: {}, Timeout: {}", lockKey, lockValue, expireTimeInMs, timeoutInMs);

        // Vòng lặp thử lại liên tục đến khi hết timeout hoặc chiếm được khóa
        while (System.currentTimeMillis() < endTime) {
            if (tryLock(lockKey, lockValue, expireTimeInMs)) {
                return true;
            }
            try {
                // Tránh làm nghẽn Redis bằng cách nghỉ ngắn 50ms trước khi thử lại
                Thread.sleep(50);
            } catch (InterruptedException e) {
                log.warn("Thread bị ngắt quãng khi đang đợi lock: {}", lockKey, e);
                Thread.currentThread().interrupt();
                return false;
            }
        }
        log.warn("Không thể acquire lock {} trong vòng {} ms (timeout)", lockKey, timeoutInMs);
        return false;
    }

    @Override
    public boolean releaseLock(String lockKey, String lockValue) {
        try {
            log.debug("Cố gắng release lock: {} với value: {}", lockKey, lockValue);
            // Biên dịch và chạy Lua Script nguyên tử qua execute()
            RedisScript<Long> redisScript = new DefaultRedisScript<>(RELEASE_LOCK_SCRIPT, Long.class);
            Long result = redisTemplate.execute(redisScript, Collections.singletonList(lockKey), lockValue);
            return Long.valueOf(1).equals(result);
        } catch (Exception e) {
            log.error("Lỗi khi cố gắng release lock: {}", lockKey, e);
            return false;
        }
    }
}
```

---

## 4. Hướng dẫn sử dụng Programmatic Lock trong Mã nguồn Nghiệp vụ

Khi sử dụng khóa lập trình, lập trình viên bắt buộc phải tuân theo cấu trúc mẫu **`try-finally`** để đảm bảo giải phóng khóa ngay cả khi nghiệp vụ xảy ra lỗi ngoại lệ (exception).

### 4.1. Ví dụ thực tế: Thực hiện giao dịch ví của khách hàng

Dưới đây là mã mẫu tích hợp khóa phân tán để đồng bộ giao dịch ví điện tử:

```java
import com.common.service.contract.IDistributedLockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class WalletService {

    private final IDistributedLockService lockService;
    private final WalletRepository walletRepository;

    public void deductMoney(Long userId, double amount) {
        String lockKey = "lock:wallet:user:" + userId;
        // Tạo token định danh duy nhất cho thread này
        String lockValue = UUID.randomUUID().toString(); 
        
        // Cố gắng lấy khóa trong vòng tối đa 3 giây, TTL của khóa là 10 giây
        boolean isLocked = lockService.tryLockWithTimeout(lockKey, lockValue, 10000, 3000);
        
        if (!isLocked) {
            log.warn("Hệ thống bận, không thể thực hiện giao dịch cho user: {}", userId);
            throw new RuntimeException("Hệ thống đang xử lý yêu cầu khác của bạn. Vui lòng thử lại sau.");
        }
        
        try {
            log.info("Bắt đầu xử lý giao dịch ví cho user: {}", userId);
            
            // Lấy thông tin ví và kiểm tra số dư
            Wallet wallet = walletRepository.findByUserId(userId)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy ví người dùng."));
                    
            if (wallet.getBalance() < amount) {
                throw new RuntimeException("Số dư tài khoản không đủ.");
            }
            
            // Trừ tiền và lưu lại
            wallet.setBalance(wallet.getBalance() - amount);
            walletRepository.save(wallet);
            
            log.info("Xử lý giao dịch ví thành công cho user: {}", userId);
        } finally {
            // Luôn luôn giải phóng khóa trong khối finally
            boolean released = lockService.releaseLock(lockKey, lockValue);
            if (released) {
                log.info("Giải phóng khóa thành công: {}", lockKey);
            } else {
                log.warn("Không thể giải phóng khóa (có thể đã hết TTL tự động hoặc không trùng token): {}", lockKey);
            }
        }
    }
}
```

---

## 5. Quy trình Kiểm thử (Testing)

Nhằm đảm bảo cơ chế loại trừ tương hỗ hoạt động tốt, mã nguồn test được thiết kế mô phỏng tranh chấp đa luồng bằng `ExecutorService` và `CountDownLatch`.

Mã kiểm thử [RedisDistributedLockServiceTest.java](file:///d:/Du_an_ca_nhan/Base-Codebase/Common/src/test/java/com/common/service/impl/RedisDistributedLockServiceTest.java):

- **Test đơn luồng:** Xác nhận cơ chế lock thành công lần đầu, thất bại ở lần 2 khi chưa release, và thành công lại sau khi release.
- **Test đa luồng (Concurrency):** Bắn ra 10 luồng đồng thời (sử dụng `CountDownLatch` điều phối kích hoạt). Đảm bảo chỉ có duy nhất 1 luồng lấy được khóa thành công, 9 luồng còn lại nhận kết quả `false` (thất bại).

Chạy toàn bộ test suite để xác minh độ ổn định bằng lệnh:

```bash
.\gradlew.bat test
```

---

## 6. Các Best Practices khi áp dụng Khóa Phân Tấn

1. **Đặt tên Key khoa học:** Key lưu trên Redis nên được phân cấp rõ ràng theo định dạng: `lock:{module}:{business_entity}:{entity_id}`.
   - Ví dụ: `lock:order:payment:1509`
2. **Thiết lập TTL (Expire Time) hợp lý:**
   - Không đặt quá ngắn: Tránh việc tác vụ chưa chạy xong khóa đã hết hạn dẫn đến mất tính năng loại trừ tương hỗ.
   - Không đặt quá dài: Tránh việc khi node bị crash, hệ thống bị treo khóa quá lâu gây ảnh hưởng đến trải nghiệm người dùng.
   - Nguyên tắc: TTL nên lớn hơn ít nhất gấp 2-3 lần thời gian thực thi trung bình của tác vụ nghiệp vụ.
3. **Sử dụng try-finally luôn luôn:** Tránh việc bỏ sót mở khóa dẫn đến nghẽn tiến trình của các yêu cầu tiếp theo đến khi hết TTL.
4. **Không lạm dụng khóa phân tán:** Việc sử dụng Distributed Lock làm tăng độ trễ (latency) của API do phải giao tiếp qua mạng với Redis. Chỉ áp dụng cho các tài nguyên thực sự nhạy cảm liên quan đến tiền bạc, thanh toán, đồng bộ dữ liệu quan trọng hoặc tác vụ ngầm chạy định kỳ.
5. **Chọn ShedLock cho lập lịch và Programmatic Lock cho nghiệp vụ:**
   - `@SchedulerLock` là cách tốt nhất cho `@Scheduled` vì không cần sửa đổi mã nguồn nghiệp vụ nhiều, cấu hình khai báo dạng declarative.
   - `IDistributedLockService` cung cấp sự linh hoạt tối đa cho luồng nghiệp vụ động, xử lý timeout và retry chi tiết.
