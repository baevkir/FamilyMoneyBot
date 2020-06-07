package com.familymoney.bot.service;

import com.familymoney.clients.AccountClient;
import com.familymoney.clients.UserClient;
import com.familymoney.model.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;


@Slf4j
@Component
public class AccountServiceImpl implements AccountService {
    private AccountClient accountClient;
    private UserClient userClient;

    public AccountServiceImpl(AccountClient accountClient, UserClient userClient) {
        this.accountClient = accountClient;
        this.userClient = userClient;
    }

    @Override
    public Flux<Account> getAllForTelegramUserId(Integer telegramId) {
        Objects.requireNonNull(telegramId, "telegramId should be not null.");
        return userClient.getByTelegramId(telegramId)
                .flatMapMany(user -> accountClient.getAll(user.getId()));
    }

    @Override
    public Mono<Void> shareForUser(Integer sourceTelegramId, String sourceAccountName, String targetUserName) {
        log.info("Share for user {} form {}", sourceTelegramId, targetUserName);
        return Mono.empty();
    }

}
