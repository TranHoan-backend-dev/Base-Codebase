package com.common.repository;

import com.common.model.BaseModel;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
interface BasePagingAndSortingRepository extends PagingAndSortingRepository<BaseModel, String> {
}
