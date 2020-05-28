package com.familymoney.accounts.persistence.repository;

import com.familymoney.accounts.persistence.entity.AccountEntity;
import com.familymoney.accounts.persistence.entity.UserAccountEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface UserAccountRepository extends R2dbcRepository<UserAccountEntity, Long> {
    Flux<UserAccountEntity> getAllByUserId(Long userId);
}
