package com.familymoney.telegrambot.business.service.payment;

import com.familymoney.telegrambot.business.model.PaymentCategory;
import com.familymoney.telegrambot.business.model.PaymentType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PaymentCategoryService {
    Flux<PaymentCategory> getAll(Long chatId);
    Mono<PaymentCategory> create(PaymentCategory paymentType);
    Mono<PaymentCategory> resolvePaymentType(PaymentCategory paymentType);
}
