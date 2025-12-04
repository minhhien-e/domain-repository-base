# Domain Repository Base

Thư viện cung cấp các lớp cơ sở (base classes) và giao diện (interfaces) để triển khai các Repository theo mô hình Domain-Driven Design (DDD), hỗ trợ đặc biệt cho hạ tầng MongoDB.

## Tổng quan

Dự án này giúp đơn giản hóa việc triển khai pattern Repository trong các ứng dụng DDD. Nó tách biệt các định nghĩa domain khỏi việc triển khai hạ tầng, giúp kiến trúc sạch sẽ và dễ bảo trì hơn.

## Các Module

Dự án được chia thành hai module chính, có thể sử dụng độc lập tùy theo nhu cầu:

### 1. Domain Core (`:domain`)
- **Artifact ID**: `domain-repository-core`
- **Mô tả**: Chứa các interface và base class thuần túy cho Domain layer. Không phụ thuộc vào framework hạ tầng nào.
- **Thành phần**:
    - `DomainEntityRepository`: Interface gốc.
    - `Trackable`, `DirtyTracking`: Cơ chế theo dõi thay đổi.
    - Base classes cho Aggregate và Entity.

### 2. Infrastructure Mongo (`:infrastructure:mongo`)
- **Artifact ID**: `domain-repository-mongo`
- **Mô tả**: Triển khai MongoDB cho các repository.
- **Dependency**: Tự động bao gồm `domain-repository-core`.
- **Thành phần**:
    - `AbstractAggregateMongoRepository`: Base class cho Aggregate Repository.
    - `AbstractEntityMongoRepository`: Base class cho Entity Repository.

## Hướng dẫn sử dụng

### Cách 1: Sử dụng trọn bộ (Khuyên dùng cho module Infrastructure)
Nếu bạn đang cài đặt tầng hạ tầng (Infrastructure Layer), hãy import module mongo. Nó sẽ tự động kéo theo module core.

```groovy
implementation 'com.github.minhhien-e:domain-repository-base:domain-repository-mongo:1.0.0'
```

### Cách 2: Sử dụng riêng Domain Core (Khuyên dùng cho module Domain)
Nếu bạn đang viết code trong tầng Domain và muốn giữ nó sạch (không phụ thuộc vào Spring hay Mongo), chỉ import module core:

```groovy
implementation 'com.github.minhhien-e:domain-repository-base:domain-repository-core:1.0.0'
```

### Ví dụ triển khai

#### 1. Tại Domain Layer (chỉ phụ thuộc `domain-repository-core`)
```java
public interface MyAggregateRepository extends DomainEntityRepository<MyAggregate> {
    // Các phương thức query nghiệp vụ
    List<MyAggregate> findByStatus(String status);
}
```

#### 2. Tại Infrastructure Layer (phụ thuộc `domain-repository-mongo`)
```java
@Repository
public class MyAggregateMongoRepository extends AbstractAggregateMongoRepository<MyAggregate, MyMongoDocument> implements MyAggregateRepository {
    
    public MyAggregateMongoRepository(MongoTemplate mongoTemplate) {
        super(mongoTemplate);
    }

    @Override
    protected Class<?> getChildEntityClass(AggregateChild child) {
        // Mapping logic
    }
    
    // Triển khai các query method
}
```

## Yêu cầu
- Java 17
- Spring Boot 3.5.3 (cho module infrastructure)
