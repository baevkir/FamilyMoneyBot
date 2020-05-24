package com.familymoney.users.persistence;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<UserEntity, Long> {
    Mono<UserEntity> findByTelegramId(Long telegramId);
    Mono<UserEntity> findByUserName(String userName);
}
