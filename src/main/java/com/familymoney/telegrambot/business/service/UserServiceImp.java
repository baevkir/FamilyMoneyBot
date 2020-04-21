package com.familymoney.telegrambot.business.service;

import com.familymoney.telegrambot.business.mapper.UserMapper;
import com.familymoney.telegrambot.business.model.BotUser;
import com.familymoney.telegrambot.persistence.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;
import reactor.core.publisher.Mono;

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
    public Mono<BotUser> resolve(BotUser user) {
        return userRepository.findByTelegramId(user.getTelegramId())
                .map(userMapper::fromEntity)
                .switchIfEmpty(create(user));
    }
}
