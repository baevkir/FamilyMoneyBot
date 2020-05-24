package com.familymoney.accounts.bussines.service;

import com.familymoney.accounts.bussines.mapper.AccountMapper;
import com.familymoney.accounts.persistence.entity.AccountEntity;
import com.familymoney.accounts.persistence.repository.AccountRepository;
import com.familymoney.model.Account;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;


@Component
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository;
    private AccountMapper accountMapper;

    public AccountServiceImpl(
            AccountRepository accountRepository,
            AccountMapper accountMapper
    ) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    public Flux<Account> getAll(Long userId) {
        Objects.requireNonNull(userId, "UserId should be not null.");
        return accountRepository.findAllById(getAllIds(userId))
                .map(account -> accountMapper.fromEntity(account, userId));
    }

    @Override
    public Flux<Long> getAllIds(Long userId) {
        Objects.requireNonNull(userId, "UserId should be not null.");
        return accountRepository.getAllByUserIdsContains(userId).map(AccountEntity::getId);
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
}
