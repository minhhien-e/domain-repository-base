package io.github.domain.entity.base;

import io.github.domain.base.Trackable;

import java.util.Set;
import java.util.UUID;

public abstract class DomainEntity implements Trackable {
    private final UUID id;
    protected final Trackable trackable;

    public DomainEntity(UUID id, Trackable trackable) {
        this.id = id;
        this.trackable = trackable;
    }

    public DomainEntity(Trackable trackable) {
        this.trackable = trackable;
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    @Override
    public Set<String> getChangedProperties() {
        return trackable.getChangedProperties();
    }

    @Override
    public void markChanged(String property) {
        trackable.markChanged(property);
    }

    @Override
    public void clearChanges() {
        trackable.clearChanges();
    }

    @Override
    public boolean hasChanges() {
        return trackable.hasChanges();
    }
}
