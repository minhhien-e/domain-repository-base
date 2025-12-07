package io.github.infrastructure.mongo.entity;

import io.github.infrastructure.mongo.entity.base.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Document(collection = "outbox")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class OutboxEntity extends MongoEntity {
    private String aggregateType;
    private String aggregateId;
    private String type;
    private Object payload;
    private String status; // PENDING, PROCESSED, FAILED
}
