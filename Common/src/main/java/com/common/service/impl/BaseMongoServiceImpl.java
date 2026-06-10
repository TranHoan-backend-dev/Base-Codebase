package com.common.service.impl;

import com.common.model.nosql.BaseModel;
import com.common.repository.mongo.BaseRepository;
import com.common.service.contract.IBaseService;
import com.common.dto.request.PagingRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Lớp implementation base service cho NoSQL MongoDB.<br/>
 * Created at 10/06/2026
 *
 * @param <TEntity>     Kiểu dữ liệu Document kế thừa từ BaseModel (NoSQL)
 * @param <TId>         Kiểu dữ liệu khóa chính Document (thường là String)
 * @param <TRepository> Kiểu dữ liệu Repository kế thừa từ BaseRepository (NoSQL)
 * @author txhoan
 */
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
public abstract class BaseMongoServiceImpl<
        TEntity extends BaseModel,
        TId,
        TRepository extends BaseRepository<TEntity, TId>>
        implements IBaseService<TEntity, TId> {

    TRepository repository;

    @Override
    @Transactional
    public TEntity create(TEntity entity) {
        log.info("Creating Mongo document: {}", entity);
        return repository.save(entity);
    }

    @Override
    @Transactional
    public void update(TEntity entity) {
        log.info("Updating Mongo document: {}", entity);
        repository.save(entity);
    }

    @Override
    @Transactional
    public void delete(TEntity entity) {
        log.info("Deleting Mongo document: {}", entity);
        repository.delete(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TEntity> findById(TId id) {
        log.info("Finding Mongo document by id: {}", id);
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public TEntity findByIdOrThrow(TId id) {
        log.info("Finding Mongo document by id or throw: {}", id);
        return repository.findByIdOrThrow(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TEntity> getPaginated(PagingRequest request) {
        log.info("Getting paginated Mongo documents: {}", request);
        return repository.findAll(request.pageable());
    }
}
