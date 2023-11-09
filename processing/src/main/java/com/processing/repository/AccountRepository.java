package com.processing.repository;

import com.processing.model.entity.AccountEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountRepository extends CrudRepository<AccountEntity, Long> {
    List<AccountEntity> getByUserId(Long userId);
}
