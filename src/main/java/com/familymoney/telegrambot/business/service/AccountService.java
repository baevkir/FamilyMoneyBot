package com.familymoney.telegrambot.business.service;

import com.familymoney.telegrambot.business.model.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {
    Flux<Account> getAll(Long chatId);
    Mono<Account> get(Long id);
    Mono<Account> create(Account paymentType);
    Mono<Account> resolve(Account paymentType);


}
