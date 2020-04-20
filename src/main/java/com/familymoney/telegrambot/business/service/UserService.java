package com.familymoney.telegrambot.business.service;

import com.familymoney.telegrambot.business.model.BotUser;
import org.telegram.telegrambots.meta.api.objects.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Flux<BotUser> getAllUsers();
    Mono<BotUser> getUserById(Integer id);
    Mono<Boolean> exist(User user);
    Mono<BotUser> create(User user);
    Mono<BotUser> resolveUser(User user);
}
