package com.familymoney.accounts.bussines.service;

import com.familymoney.model.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {
    Flux<Account> getAll(Long userId);
    Flux<Long> getAllIds(Long userId);
    Mono<Account> get(Long id);
    Mono<Account> find(Long userId, String name);
    Mono<Account> create(Account account);
    Mono<Account> resolve(Account account);
}
