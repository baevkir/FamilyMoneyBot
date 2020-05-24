package com.familymoney.categories.bussines.service;

import com.familymoney.model.Account;
import com.familymoney.model.PaymentCategory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CategoryService {
    Flux<PaymentCategory> getAll(Long userId);
    Flux<Long> getAllIds(Long userId);
    Mono<PaymentCategory> get(Long id);
    Mono<PaymentCategory> find(Long userId, String name);
    Mono<PaymentCategory> create(PaymentCategory paymentCategory);
    Mono<PaymentCategory> resolve(PaymentCategory paymentCategory);
}
