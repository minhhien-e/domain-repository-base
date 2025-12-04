package io.github.infrastructure.mongo.repository.base;

import io.github.domain.entity.base.DomainEntity;
import io.github.infrastructure.mongo.entity.base.MongoEntity;
import org.springframework.data.mongodb.core.query.Update;

import java.lang.reflect.Field;

public interface EntityMongoRepository<D extends DomainEntity, E extends MongoEntity> {
    void save(D domainEntity);

    Class<?> getEntityClass();

    E toEntity(D domainEntity);

    default void setUpdateValue(Update update, String property, Object entity) {
        try {
            Field field = entity.getClass().getDeclaredField(property);
            field.setAccessible(true);
            Object value = field.get(entity);
            update.set(field.getName(), value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
