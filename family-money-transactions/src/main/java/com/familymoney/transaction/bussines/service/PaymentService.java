package com.familymoney.transaction.bussines.service;

import com.familymoney.model.Payment;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PaymentService {
    Flux<Payment> getAllByAccountIds(Long... accountIds);
    Mono<Payment> create(Payment payment);
}
