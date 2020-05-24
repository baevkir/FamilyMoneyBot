package com.familymoney.users.bussines.service;

import com.familymoney.model.BotUser;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<BotUser> create(BotUser user);
    Mono<BotUser> get(Long id);
    Mono<BotUser> getByTelegramId(Long telegramId);
    Mono<BotUser> getByUserName(String userName);
    Mono<BotUser> resolve(BotUser user);
}
