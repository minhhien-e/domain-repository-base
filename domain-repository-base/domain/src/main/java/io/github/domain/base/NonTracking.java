package io.github.domain.base;

import java.util.Set;

public class NonTracking implements Trackable {
    @Override
    public Set<String> getChangedProperties() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void markChanged(String property) {
        throw new UnsupportedOperationException("Not supported yet.");

    }

    @Override
    public void clearChanges() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean hasChanges() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

