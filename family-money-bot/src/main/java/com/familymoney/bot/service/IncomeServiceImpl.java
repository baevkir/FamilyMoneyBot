package com.familymoney.bot.service;

import com.familymoney.bot.client.AccountClient;
import com.familymoney.bot.client.IncomeClient;
import com.familymoney.bot.client.UserClient;
import com.familymoney.model.Account;
import com.familymoney.model.BotUser;
import com.familymoney.model.Income;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.time.LocalDate;
import java.util.Objects;

@Slf4j
@Service
public class IncomeServiceImpl implements IncomeService {

    private IncomeClient incomeClient;
    private UserClient userClient;
    private AccountClient accountClient;

    public IncomeServiceImpl(
            IncomeClient incomeClient,
            UserClient userClient,
            AccountClient accountClient
    ) {
        this.incomeClient = incomeClient;
        this.userClient = userClient;
        this.accountClient = accountClient;
    }

    @Override
    public Flux<Income> getAllByTelegramUserId(Integer telegramId) {
        Objects.requireNonNull(telegramId, "telegramId is null.");
        return userClient.getByTelegramId(telegramId)
                .flatMapMany(user -> accountClient.getAllIds(user.getId()))
                .collectList()
                .flatMapMany(incomeClient::findAllByAccountIds);
    }

    @Override
    public Mono<Income> create(Income income) {
        return prepareData(income).flatMap(data -> {
            income.setDate(LocalDate.now());
            income.setUser(data.getT1());
            income.setAccount(data.getT2());

            return incomeClient.create(income);
        });
    }

    private Mono<Tuple2<BotUser, Account>> prepareData(Income income) {
        return userClient.resolveUser(income.getUser()).flatMap(user -> {
            Account account = income.getAccount();
            account.setUserId(user.getId());
            return accountClient.resolveAccount(account).map(result -> Tuples.of(user, result));
        });
    }
}
