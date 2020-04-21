package com.familymoney.telegrambot.persistence.repository;

import com.familymoney.telegrambot.persistence.entity.UserEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<UserEntity, Long> {
    Mono<UserEntity> findByTelegramId(Integer telegramId);
}
