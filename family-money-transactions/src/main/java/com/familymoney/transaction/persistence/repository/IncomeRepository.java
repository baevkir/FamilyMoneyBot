package com.familymoney.transaction.persistence.repository;

import com.familymoney.transaction.persistence.entity.IncomeEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface IncomeRepository extends R2dbcRepository<IncomeEntity, Long> {
    Flux<IncomeEntity> findAllByAccountId(Long accountId);
}
