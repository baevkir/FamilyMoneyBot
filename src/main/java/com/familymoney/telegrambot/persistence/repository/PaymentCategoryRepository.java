package com.familymoney.telegrambot.persistence.repository;

import com.familymoney.telegrambot.persistence.entity.PaymentCategoryEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PaymentCategoryRepository extends ReactiveCrudRepository<PaymentCategoryEntity, Long> {
    Flux<PaymentCategoryEntity> findAllByChatId(Long chatId);
    Mono<PaymentCategoryEntity> findByName(String name);
}
