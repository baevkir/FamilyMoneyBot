package com.familymoney.transaction.bussines.service;

import com.familymoney.model.Payment;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PaymentService {
    Flux<Payment> getAll(Long userId);
    Mono<Payment> create(Payment payment);
}
