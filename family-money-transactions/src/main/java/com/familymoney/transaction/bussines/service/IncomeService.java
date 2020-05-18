package com.familymoney.transaction.bussines.service;

import com.familymoney.model.Income;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IncomeService {
    Flux<Income> getAllByAccountIds(Long... accountIds);
    Mono<Income> create(Income income);
}
