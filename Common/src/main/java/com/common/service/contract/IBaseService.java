package com.common.service.contract;

import com.common.dto.request.PagingRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public interface IBaseService<TEntity, TId> {
    TEntity create(TEntity entity);

    void update(TEntity entity);

    void delete(TEntity entity);

    Optional<TEntity> findById(TId id);

    @Transactional(readOnly = true) TEntity findByIdOrThrow(TId id);

    Page<TEntity> getPaginated(PagingRequest request);
}
