package com.familymoney.telegrambot.business.service;

import com.familymoney.telegrambot.business.mapper.AccountMapper;
import com.familymoney.telegrambot.business.model.Account;
import com.familymoney.telegrambot.persistence.entity.account.UserAccountEntity;
import com.familymoney.telegrambot.persistence.repository.account.AccountRepository;
import com.familymoney.telegrambot.persistence.repository.account.UserAccountRepository;
import com.google.common.collect.Iterables;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.testcontainers.shaded.com.google.common.collect.Iterables.toArray;

@Component
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository;
    private UserAccountRepository userAccountRepository;
    private AccountMapper accountMapper;
    private UserService userService;

    public AccountServiceImpl(AccountRepository accountRepository,
                              UserAccountRepository userAccountRepository,
                              AccountMapper accountMapper,
                              UserService userService) {
        this.accountRepository = accountRepository;
        this.userAccountRepository = userAccountRepository;
        this.accountMapper = accountMapper;
        this.userService = userService;
    }

    @Override
    public Flux<Account> getAll(Long userId) {
        Objects.requireNonNull(userId, "UserId should be not null.");
        return accountRepository.findAllById(getAllIds(userId))
                .map(account -> accountMapper.fromEntity(account, userId));
    }

    @Override
    public Flux<Account> getAllForTelegramUserId(Integer telegramId) {
        Objects.requireNonNull(telegramId, "telegramId should be not null.");
        return userService.getByTelegramId(telegramId)
                .flatMapMany(user -> getAll(user.getId()));
    }

    @Override
    public Flux<Long> getAllIds(Long userId) {
        Objects.requireNonNull(userId, "UserId should be not null.");
        return userAccountRepository.findAllByUserId(userId).map(UserAccountEntity::getAccountId);
    }

    @Override
    public Mono<Account> get(Long id) {
        Objects.requireNonNull(id, "Id should be not null.");
        return accountRepository.findById(id).map(accountMapper::fromEntity);
    }

    @Override
    public Mono<Account> find(Long userId, String name) {
        Objects.requireNonNull(userId, "Payment type name should be not null.");
        Objects.requireNonNull(name, "UserId should be not null.");
        return getAllIds(userId)
                .collect(Collectors.toList())
                .filter(CollectionUtils::isNotEmpty)
                .flatMap(accountIds -> accountRepository.findByIdInAndName(accountIds, name))
                .map(entity -> accountMapper.fromEntity(entity, userId));
    }

    @Override
    @Transactional
    public Mono<Account> create(Account account) {
        Objects.requireNonNull(account.getName(), "Payment type name should be not null.");
        Objects.requireNonNull(account.getUserId(), "UserId should be not null.");
        return Mono.fromSupplier(() -> accountMapper.toEntity(account))
                .flatMap(accountRepository::save)
                .flatMap(accountEntity ->
                        userAccountRepository.save(UserAccountEntity.builder()
                                .userId(account.getUserId())
                                .accountId(accountEntity.getId())
                                .build()).thenReturn(accountEntity)
                )
                .map(accountMapper::fromEntity);
    }

    @Override
    public Mono<Account> resolve(Account account) {
        if (account.getId() != null) {
            return get(account.getId());
        }
        return find(account.getUserId(), account.getName())
                .switchIfEmpty(create(account));
    }

    private static Collection<Object[]> toPersistentCollection(Collection<Long> ids) {
        return Collections.singleton(new Object[]{"5"});
//        return ids.stream().map(ArrayUtils::toArray).collect(Collectors.toList());
    }
}
