package com.familymoney.telegrambot.persistence.repository.category;

import com.familymoney.telegrambot.persistence.entity.category.PaymentCategoryEntity;
import org.reactivestreams.Publisher;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.Flow;

public interface PaymentCategoryRepository extends R2dbcRepository<PaymentCategoryEntity, Long> {
    Mono<PaymentCategoryEntity> findByIdInAndName(List<Long> ids, String name);
}
