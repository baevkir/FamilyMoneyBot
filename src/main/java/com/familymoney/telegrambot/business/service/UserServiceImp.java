package com.familymoney.telegrambot.business.service;

import com.familymoney.telegrambot.business.model.BotUser;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {
    Map<Integer, BotUser> users = new HashMap<>();

    @Override
    public Flux<BotUser> getAllUsers() {
        return Flux.fromIterable(users.values());
    }

    @Override
    public Mono<BotUser> getUserById(Integer id) {
        return Mono.fromSupplier(() -> users.get(id));
    }

    @Override
    public Mono<Boolean> exist(User user) {
        return Mono.just(users.containsKey(user.getId()));
    }

    @Override
    public Mono<BotUser> create(User user) {
        BotUser botUser = new BotUser();
        botUser.setId(user.getId());
        botUser.setFirstName(user.getFirstName());
        botUser.setLastName(user.getLastName());

        String userName = Optional.ofNullable(user.getUserName()).orElseGet(() -> user.getFirstName() + " " + user.getLastName());
        botUser.setUserName(userName);
        users.put(user.getId(), botUser);
        return Mono.just(botUser);
    }

    @Override
    public Mono<BotUser> resolveUser(User user) {
        return exist(user).flatMap(exist -> {
            if (exist) {
                return getUserById(user.getId());
            }
            return create(user);
        });
    }
}
