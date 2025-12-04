package io.github.infrastructure.mongo.repository.base;

import io.github.domain.entity.base.DomainEntity;
import io.github.infrastructure.mongo.entity.base.MongoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

@RequiredArgsConstructor
public abstract class AbstractEntityMongoRepository<D extends DomainEntity, E extends MongoEntity> implements EntityMongoRepository<D, E> {
    protected final MongoTemplate mongoTemplate;

    @Override
    public void save(D domainEntity) {
        if (domainEntity.getChangedProperties().isEmpty())
            return;
        Query query = new Query(Criteria.where("id").is(domainEntity.getId()));
        var entity = toEntity(domainEntity);
        if (!mongoTemplate.exists(query, getEntityClass())) {
            mongoTemplate.save(entity);
            return;
        }
        Update update = new Update();
        for (String dirtyField : domainEntity.getChangedProperties()) {
            setUpdateValue(update, dirtyField, entity);
        }
        mongoTemplate.upsert(query, update, getEntityClass());
    }
}
