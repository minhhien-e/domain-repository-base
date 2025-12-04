package io.github.infrastructure.mongo.repository.base;

import io.github.domain.aggregate.base.AggregateChild;
import io.github.domain.aggregate.base.AggregateRoot;
import io.github.domain.base.Trackable;
import io.github.domain.entity.base.DomainEntity;
import io.github.domain.enums.DomainStatus;
import io.github.infrastructure.mongo.entity.base.MongoEntity;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Map;
import java.util.Set;

//Tối ưu bulk operation
public abstract class AbstractAggregateMongoRepository<A extends AggregateRoot, E extends MongoEntity>
        extends AbstractEntityMongoRepository<A, E> {

    protected AbstractAggregateMongoRepository(MongoTemplate mongoTemplate) {
        super(mongoTemplate);
    }

    protected abstract Class<?> getChildEntityClass(AggregateChild child);

    protected abstract Object toChildEntity(DomainEntity entity);

    @Override
    public void save(A aggregate) {
        super.save(aggregate);
        for (Map.Entry<Trackable, Set<String>> dirtyNested : aggregate.getNestedChangedProperties().entrySet()) {
            AggregateChild dirtyEntity = (AggregateChild) dirtyNested.getKey();
            Set<String> dirtyProps = dirtyNested.getValue();
            saveChild(dirtyEntity, dirtyProps);
        }
    }

    private void saveChild(AggregateChild entity, Set<String> dirtyProps) {
        Update update = new Update();
        Query query = new Query(Criteria.where("id").is(entity.getId()));
        Class<?> childClass = getChildEntityClass(entity);
        if (childClass == null) {
            return;
        }
        var ormEntity = toChildEntity(entity);
        if (!mongoTemplate.exists(query, childClass)) {
            mongoTemplate.save(ormEntity);
            return;
        }
        if (dirtyProps.contains(DomainStatus.DELETED.name()))
            mongoTemplate.remove(query, childClass);
        for (String property : dirtyProps) {
            setUpdateValue(update, property, ormEntity);
        }
        mongoTemplate.upsert(query, update, childClass);
    }
}
