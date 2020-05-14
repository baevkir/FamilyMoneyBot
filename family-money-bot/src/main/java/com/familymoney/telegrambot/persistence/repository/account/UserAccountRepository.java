package com.familymoney.telegrambot.persistence.repository.account;

import com.familymoney.telegrambot.persistence.entity.account.AccountEntity;
import com.familymoney.telegrambot.persistence.entity.account.UserAccountEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserAccountRepository extends R2dbcRepository<UserAccountEntity, Long> {
    Flux<UserAccountEntity> findAllByUserId(Long userId);
}
