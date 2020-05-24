package com.familymoney.bot.service;

import com.familymoney.model.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {
    Flux<Account> getAllForTelegramUserId(Integer telegramId);
    Mono<Void> shareForUser(Integer sourceTelegramId, String sourceAccountName, String targetUserName);
}
