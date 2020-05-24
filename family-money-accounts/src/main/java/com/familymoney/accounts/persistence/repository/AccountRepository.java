package com.familymoney.accounts.persistence.repository;

import com.familymoney.accounts.persistence.entity.AccountEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface AccountRepository extends ReactiveCrudRepository<AccountEntity, Long> {
    Flux<AccountEntity> getAllByUserIdsContains(Long userId);
}
