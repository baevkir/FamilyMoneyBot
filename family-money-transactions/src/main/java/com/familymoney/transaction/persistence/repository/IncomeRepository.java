package com.familymoney.transaction.persistence.repository;

import com.familymoney.transaction.persistence.entity.IncomeEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;


public interface IncomeRepository extends ReactiveCrudRepository<IncomeEntity, Long> {
    Flux<IncomeEntity> findAllByAccountId(Long accountId);
}
