package com.familymoney.accounts.web.controller;

import com.familymoney.accounts.bussines.service.AccountService;
import com.familymoney.model.Account;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("family-money/v1/users/{userId}/accounts")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping()
    public Flux<Account> getAll(@PathVariable Long userId) {
        return accountService.getAll(userId);
    }

    @GetMapping(value = "ids")
    public Flux<Long> getAllIds(@PathVariable Long userId) {
        return accountService.getAllIds(userId);
    }

    @PutMapping(value = "resolve")
    public Mono<Account> resolve(@PathVariable Long userId, @RequestBody Account account) {
        account.setUserId(userId);
        return accountService.resolve(account);
    }
}
