package io.github.domain.base;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class DirtyTracking implements Trackable {
    private final Set<String> dirtyProperties = new HashSet<>();

    @Override
    public Set<String> getChangedProperties() {
        return Collections.unmodifiableSet(dirtyProperties);
    }

    @Override
    public void clearChanges() {
        dirtyProperties.clear();
    }

    @Override
    public boolean hasChanges() {
        return !dirtyProperties.isEmpty();
    }

    @Override
    public void markChanged(String property) {
        dirtyProperties.add(property);
    }
}
