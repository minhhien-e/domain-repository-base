# Domain Repository Base

Thư viện cung cấp các lớp cơ sở (base classes) và giao diện (interfaces) để triển khai Repository theo mô hình **Domain-Driven Design (DDD)**, hỗ trợ tách biệt hoàn toàn giữa Domain và Infrastructure.

---

## 📦 Cài Đặt

Thư viện được chia thành 2 module chính. Tùy thuộc vào layer bạn đang làm việc, hãy chọn dependency phù hợp.

### 1. Domain Layer (`:domain-core`)
Dùng cho các module chứa nghiệp vụ cốt lõi (Domain), nơi bạn **định nghĩa** các Entity và Repository Interface. Module này **không** phụ thuộc vào Spring hay MongoDB.

```groovy
implementation 'com.github.minhhien-e:domain-repository-base:domain-repository-core:1.0.0'
```

### 2. Infrastructure Layer (`:domain-repository-mongo`)
Dùng cho các module triển khai hạ tầng (Infrastructure), nơi bạn **thực thi** các Repository Interface bằng MongoDB. Module này đã bao gồm `:domain-core`.

```groovy
implementation 'com.github.minhhien-e:domain-repository-base:domain-repository-mongo:1.0.0'
```

---

## 🛠 Chức năng: Mô hình hóa Domain (Domain Modeling)

Phần này hướng dẫn cách sử dụng `:domain-core` để xây dựng các Aggregate và Entity.

### 1. Định nghĩa Aggregate Root
Kế thừa lớp `Aggregate` để tạo một Aggregate Root. Lớp này hỗ trợ cơ chế **Dirty Tracking** để tối ưu hóa việc lưu trữ (chỉ lưu các trường thay đổi).

```java
public class Order extends Aggregate<String> {
    public Order(String id) {
        super(id);
    }
    
    // Các logic nghiệp vụ...
}
```

### 2. Định nghĩa Repository Interface
Tạo interface cho repository trong Domain layer, kế thừa `DomainEntityRepository`.

```java
public interface OrderRepository extends DomainEntityRepository<String, Order> {
    List<Order> findByStatus(String status);
}
```

### 3. Cơ chế Dirty Tracking
Mọi thay đổi trên Aggregate đều nên được theo dõi để tối ưu hiệu năng khi persistence.
- **Trackable**: Interface đánh dấu khả năng theo dõi.
- **DirtyTracking**: Class hỗ trợ đánh dấu các field bị thay đổi.

---

## ⚙️ Chức năng: Triển khai Hạ tầng (Infrastructure)

Phần này hướng dẫn cách sử dụng `:domain-repository-mongo` để implement các interface đã định nghĩa ở trên.

### 1. Cấu hình
Yêu cầu hệ thống:
- Java 17+
- Spring Boot 3.5.3+

### 2. Implement Repository
Sử dụng `AbstractAggregateMongoRepository` để triển khai repository thật.

```java
@Repository
public class OrderMongoRepository extends AbstractAggregateMongoRepository<Order, OrderDocument> implements OrderRepository {

    public OrderMongoRepository(MongoTemplate mongoTemplate) {
        super(mongoTemplate);
    }

    @Override
    protected Class<OrderDocument> getDocumentClass() {
        return OrderDocument.class;
    }

    // Triển khai các query method bổ sung
    @Override
    public List<Order> findByStatus(String status) {
        // Logic query mongo...
    }
}
```

---

## 🚀 Tính năng Nâng cao

### Nested Tracking
Hỗ trợ theo dõi thay đổi trong các object lồng nhau (Nested Objects) để đảm bảo cập nhật chính xác cả cấu trúc phức tạp.
- Sử dụng `NestedTrackable` nếu entity con của bạn cần báo cáo thay đổi lên cha.

### Domain Events
Hỗ trợ `DomainEvent` để phát đi các sự kiện nghiệp vụ từ Aggregate.

---

