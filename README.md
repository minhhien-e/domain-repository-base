# Domain Repository Base

Thư viện cung cấp các lớp cơ sở (base classes) và giao diện (interfaces) để triển khai các Repository theo mô hình Domain-Driven Design (DDD), hỗ trợ đặc biệt cho hạ tầng MongoDB.

## Tổng quan

Dự án này giúp đơn giản hóa việc triển khai pattern Repository trong các ứng dụng DDD. Nó tách biệt các định nghĩa domain khỏi việc triển khai hạ tầng, giúp kiến trúc sạch sẽ và dễ bảo trì hơn.

## Các Module

Dự án được chia thành hai module chính:

### 1. Domain (`:domain`)
Chứa các trừu tượng cốt lõi và các lớp cơ sở cho tầng domain.
- **Base Interfaces**: `DomainEntityRepository` cho các thao tác repository chung.
- **Dirty Tracking**: Cơ chế theo dõi thay đổi của entity (`Trackable`, `DirtyTracking`, `NestedDirtyTracking`), cho phép cập nhật tối ưu.
- **Base Classes**: Nền tảng cho Aggregates và Entities.

### 2. Infrastructure (`:infrastructure:mongo`)
Cung cấp triển khai MongoDB cho các domain repository.
- **Abstract Implementations**: `AbstractAggregateMongoRepository` và `AbstractEntityMongoRepository` giúp giảm thiểu code lặp lại khi triển khai repository với MongoDB.

## Tính năng

- **Hỗ trợ DDD**: Được thiết kế riêng cho việc quản lý Aggregate và Entity.
- **Dirty Tracking**: Cập nhật hiệu quả chỉ những trường thay đổi vào MongoDB.
- **Thiết kế Module**: Tách biệt rõ ràng giữa hợp đồng domain và logic hạ tầng.

## Hướng dẫn sử dụng

### Tầng Domain
Định nghĩa interface repository của bạn kế thừa từ `DomainEntityRepository`:
```java
public interface MyAggregateRepository extends DomainEntityRepository<MyAggregate> {
    // Các phương thức query tùy chỉnh
}
```

### Tầng Infrastructure
Triển khai repository của bạn kế thừa từ abstract Mongo repository:
```java
@Repository
public class MyAggregateMongoRepository extends AbstractAggregateMongoRepository<MyAggregate, MyMongoDocument> implements MyAggregateRepository {
    // Chi tiết triển khai
}
```

## Yêu cầu
- Java 17
- Spring Boot 3.5.3
