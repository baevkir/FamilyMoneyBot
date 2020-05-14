package com.familymoney.telegrambot.business.service;

import com.familymoney.model.BotUser;
import com.familymoney.telegrambot.business.mapper.UserMapper;
import com.familymoney.telegrambot.persistence.repository.UserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class UserServiceImp implements UserService {
    private UserRepository userRepository;
    private UserMapper userMapper;

    public UserServiceImp(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Mono<BotUser> create(BotUser user) {
        return Mono.fromSupplier(() -> userMapper.toEntity(user))
                .flatMap(userRepository::save)
                .map(userMapper::fromEntity);
    }

    @Override
    public Mono<BotUser> get(Long id) {
        return userRepository.findById(id).map(userMapper::fromEntity);
    }

    @Override
    public Mono<BotUser> getByTelegramId(Integer telegramId) {
        Objects.requireNonNull(telegramId, "TelegramId is Null.");
        return userRepository.findByTelegramId(telegramId)
                .map(userMapper::fromEntity);
    }

    @Override
    public Mono<BotUser> getByUserName(String userName) {
        Objects.requireNonNull(userName, "userName is Null.");
        return userRepository.findByUserName(userName)
                .map(userMapper::fromEntity);
    }

    @Override
    public Mono<BotUser> resolve(BotUser user) {
        return getByTelegramId(user.getTelegramId()).switchIfEmpty(create(user));
    }
}
