package com.familymoney.telegrambot.business.service;

import com.familymoney.telegrambot.business.model.BotUser;
import org.telegram.telegrambots.meta.api.objects.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<BotUser> create(BotUser user);
    Mono<BotUser> get(Long id);
    Mono<BotUser> resolve(BotUser user);
}
