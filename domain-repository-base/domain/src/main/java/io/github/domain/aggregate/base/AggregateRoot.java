package io.github.domain.aggregate.base;

import io.github.domain.base.DirtyTracking;
import io.github.domain.base.NestedDirtyTracking;
import io.github.domain.base.NestedTrackable;
import io.github.domain.base.Trackable;
import io.github.domain.entity.base.DomainEntity;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public abstract class AggregateRoot extends DomainEntity implements NestedTrackable {

    private final NestedTrackable nestedTracking = new NestedDirtyTracking();

    public AggregateRoot() {
        super(new DirtyTracking());
    }

    public AggregateRoot(UUID id) {
        super(id, new DirtyTracking());
    }

    @Override
    public void markNestedChanged(Trackable child, String property) {
        nestedTracking.markNestedChanged(child, property);
    }

    @Override
    public Map<Trackable, Set<String>> getNestedChangedProperties() {
        return nestedTracking.getNestedChangedProperties();
    }

    @Override
    public void clearChanges() {
        super.clearChanges();
        nestedTracking.clearChanges();
    }

    @Override
    public boolean hasChanges() {
        return super.hasChanges() || nestedTracking.hasChanges();
    }
}