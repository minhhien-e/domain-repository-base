package io.github.infrastructure.mongo.entity.base;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.UUID;

@SuperBuilder
@Data
@NoArgsConstructor
public abstract class MongoEntity {
    @MongoId
    protected UUID id;
}
