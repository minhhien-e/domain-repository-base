package io.github.domain.repository;

import io.github.domain.entity.base.DomainEntity;

public interface DomainEntityRepository<T extends DomainEntity> {
    void save(T entity);
}
