package io.github.domain.aggregate.base;

import io.github.domain.base.*;
import io.github.domain.entity.base.DomainEntity;

import java.util.*;

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

    // Domain Events
    private final List<DomainEvent> domainEvents = new ArrayList<>();

    public void addDomainEvent(DomainEvent event) {
        domainEvents.add(event);
    }

    public List<DomainEvent> getDomainEvents() {
        return List.copyOf(domainEvents);
    }

    public void clearDomainEvents() {
        domainEvents.clear();
    }
}