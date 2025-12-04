package io.github.domain.base;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NestedDirtyTracking implements NestedTrackable {
    private final Map<Trackable, Set<String>> dirtyProperties = new HashMap<>();

    @Override
    public void markNestedChanged(Trackable child, String property) {
        dirtyProperties.computeIfAbsent(child, k -> new HashSet<>()).add(property);
    }

    @Override
    public Map<Trackable, Set<String>> getNestedChangedProperties() {
        return dirtyProperties;
    }

    @Override
    public void clearChanges() {
        dirtyProperties.clear();
    }

    @Override
    public boolean hasChanges() {
        return !dirtyProperties.isEmpty();
    }
}
