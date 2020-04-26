package com.familymoney.telegrambot.persistence.repository;

import com.familymoney.telegrambot.persistence.entity.UserEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends R2dbcRepository<UserEntity, Long> {
    Mono<UserEntity> findByTelegramId(Integer telegramId);
    Mono<UserEntity> findByUserName(String userName);
}
