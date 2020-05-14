package com.familymoney.telegrambot.business.service;

import com.familymoney.model.BotUser;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<BotUser> create(BotUser user);
    Mono<BotUser> get(Long id);
    Mono<BotUser> getByTelegramId(Integer telegramId);
    Mono<BotUser> getByUserName(String userName);
    Mono<BotUser> resolve(BotUser user);
}
