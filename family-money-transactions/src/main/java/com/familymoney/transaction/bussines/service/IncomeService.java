package com.familymoney.transaction.bussines.service;

import com.familymoney.model.Income;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IncomeService {
    Flux<Income> getAll(Long userId);
    Mono<Income> create(Income income);
}
