package com.familymoney.telegrambot.business.service;

import com.familymoney.telegrambot.business.model.Income;
import com.familymoney.telegrambot.business.model.Payment;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IncomeService {
    Flux<Income> getAll(Long chatId);
    Mono<Income> create(Income income);
}
