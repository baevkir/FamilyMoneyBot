package com.familymoney.telegrambot.business.service;

import com.familymoney.model.Account;
import com.familymoney.telegrambot.business.mapper.AccountMapper;
import com.familymoney.telegrambot.persistence.entity.account.UserAccountEntity;
import com.familymoney.telegrambot.persistence.repository.account.AccountRepository;
import com.familymoney.telegrambot.persistence.repository.account.UserAccountRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;


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
        return accountRepository.findAllById(getAllIds(userId))
                .filter(accountEntity -> accountEntity.getName().equals(name))
                .next()
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

    @Override
    public Mono<Void> shareForUser(Integer sourceTelegramId, String sourceAccountName, String targetUserName) {
        return userService.getByTelegramId(sourceTelegramId)
                .flatMap(user -> find(user.getId(), sourceAccountName))
                .switchIfEmpty(Mono.error(() -> new RuntimeException(String.format("Вид оплаты %s не найден", sourceAccountName))))
                .flatMap(account -> prepareAccountForUser(account, targetUserName))
                .flatMap(userAccountRepository::save)
                .then();
    }

    private Mono<UserAccountEntity> prepareAccountForUser(Account account, String userName) {
        return userService.getByUserName(userName)
                .switchIfEmpty(Mono.error(() -> new RuntimeException(String.format("Пользователь с именем %s не найден", userName))))
                .flatMap(user -> find(user.getId(), userName)
                        .hasElement()
                        .map(hasElement -> {
                            Assert.isTrue(!hasElement, () -> String.format("Вид оплаты %s уже существует для пользователя %s.", account.getName(), userName));
                            return UserAccountEntity.builder()
                                    .userId(user.getId())
                                    .accountId(account.getId())
                                    .build();
                        }));
    }
}
