package com.familymoney.users.persistence;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends R2dbcRepository<UserEntity, Long> {
    Mono<UserEntity> findByTelegramId(Long telegramId);
    Mono<UserEntity> findByUserName(String userName);
}
