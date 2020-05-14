package com.familymoney.telegrambot.persistence.repository;

import com.familymoney.telegrambot.persistence.entity.PaymentEntity;
import org.reactivestreams.Publisher;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.util.List;

public interface PaymentRepository extends R2dbcRepository<PaymentEntity, Long> {
    Flux<PaymentEntity> findAllByAccountId(Long accountId);
}
