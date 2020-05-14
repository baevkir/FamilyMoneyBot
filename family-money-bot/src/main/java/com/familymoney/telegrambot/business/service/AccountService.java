package com.familymoney.telegrambot.business.service;

import com.familymoney.telegrambot.business.model.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {
    Flux<Account> getAll(Long userId);
    Flux<Account> getAllForTelegramUserId(Integer telegramId);
    Flux<Long> getAllIds(Long userId);
    Mono<Account> get(Long id);
    Mono<Account> find(Long userId, String name);
    Mono<Account> create(Account paymentType);
    Mono<Account> resolve(Account paymentType);
    Mono<Void> shareForUser(Integer sourceTelegramId, String sourceAccountName, String targetUserName);
}
