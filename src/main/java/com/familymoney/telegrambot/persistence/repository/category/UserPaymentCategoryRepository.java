package com.familymoney.telegrambot.persistence.repository.category;

import com.familymoney.telegrambot.persistence.entity.category.PaymentCategoryEntity;
import com.familymoney.telegrambot.persistence.entity.category.UserPaymentCategoryEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserPaymentCategoryRepository extends R2dbcRepository<UserPaymentCategoryEntity, Long> {
    Flux<UserPaymentCategoryEntity> findAllByUserId(Long userId);
}
