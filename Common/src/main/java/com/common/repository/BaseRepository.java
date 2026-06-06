package com.common.repository;

import com.common.model.BaseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface BaseRepository extends JpaRepository<BaseModel, String> {
}
