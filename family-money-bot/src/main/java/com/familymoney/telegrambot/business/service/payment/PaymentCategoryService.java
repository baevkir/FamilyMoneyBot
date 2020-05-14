package com.familymoney.telegrambot.business.service.payment;

import com.familymoney.model.PaymentCategory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PaymentCategoryService {
    Flux<PaymentCategory> getAll(Long userId);
    Flux<PaymentCategory> getAllForTelegramUserId(Integer telegramId);
    Flux<Long> getAllIds(Long userId);
    Mono<PaymentCategory> get(Long id);
    Mono<PaymentCategory> find(Long userId, String name);
    Mono<PaymentCategory> create(PaymentCategory paymentType);
    Mono<PaymentCategory> resolve(PaymentCategory paymentType);
    Mono<Void> shareForUser(Integer sourceTelegramId, String targetUserName);
}
