package com.common.service.impl;

import com.common.model.sql.BaseModel;
import com.common.service.contract.IBaseService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public abstract class BaseServiceImpl<
        TEntity extends BaseModel,
        TId,
        TRepository extends JpaRepository<TEntity, TId>>
        implements IBaseService<TEntity, TId> {

}
