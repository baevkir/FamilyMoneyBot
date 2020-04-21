package com.familymoney.telegrambot.persistence.repository;

import com.familymoney.telegrambot.business.model.PaymentType;
import com.familymoney.telegrambot.persistence.entity.PaymentTypeEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PaymentTypeRepository extends ReactiveCrudRepository<PaymentTypeEntity, Long> {
    Flux<PaymentTypeEntity> findAllByChatId(Long chatId);
    Mono<PaymentTypeEntity> findByName(String name);
}
