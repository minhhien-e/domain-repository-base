package io.github.domain.base;
import java.time.LocalDateTime;
import java.util.UUID;

public interface DomainEvent {
    UUID eventId();

    LocalDateTime occurredOn();

    String type();
}
