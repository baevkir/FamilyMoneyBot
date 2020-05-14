package com.familymoney.telegrambot.business.service;

import com.familymoney.model.Income;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IncomeService {
    Flux<Income> getAllByTelegramUserId(Integer telegramId);
    Mono<Income> create(Income income);
}
