package com.familymoney.telegrambot.persistence.repository;

import com.familymoney.telegrambot.persistence.entity.AccountEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PaymentTypeRepository extends ReactiveCrudRepository<AccountEntity, Long> {
    Flux<AccountEntity> findAllByChatId(Long chatId);
    Mono<AccountEntity> findByName(String name);
}
