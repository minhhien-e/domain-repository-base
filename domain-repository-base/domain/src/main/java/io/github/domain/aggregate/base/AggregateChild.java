package io.github.domain.aggregate.base;

import io.github.domain.base.NonTracking;
import io.github.domain.entity.base.DomainEntity;

import java.util.UUID;

public abstract class AggregateChild extends DomainEntity {
    public AggregateChild(UUID id) {
        super(id, new NonTracking());
    }

    public AggregateChild() {
        super(new NonTracking());
    }
}
