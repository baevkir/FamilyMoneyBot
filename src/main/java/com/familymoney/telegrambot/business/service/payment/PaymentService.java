package com.familymoney.telegrambot.business.service.payment;

import com.familymoney.telegrambot.business.model.Payment;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PaymentService {
    Flux<Payment> getAll(Long chatId);
    Mono<Payment> create(Payment payment);
}
