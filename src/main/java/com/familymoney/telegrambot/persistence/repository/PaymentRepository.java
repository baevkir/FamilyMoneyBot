package com.familymoney.telegrambot.persistence.repository;

import com.familymoney.telegrambot.persistence.entity.PaymentEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface PaymentRepository extends ReactiveCrudRepository<PaymentEntity, Long> {
    Flux<PaymentEntity> findAllByChatId(Long chatId);
}
