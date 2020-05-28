package com.familymoney.bot.service;

import com.familymoney.bot.client.IncomeClient;
import com.familymoney.bot.client.UserClient;
import com.familymoney.model.BotUser;
import com.familymoney.model.Income;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Objects;

@Slf4j
@Service
public class IncomeServiceImpl implements IncomeService {

    private IncomeClient incomeClient;
    private UserClient userClient;

    public IncomeServiceImpl(
            IncomeClient incomeClient,
            UserClient userClient
    ) {
        this.incomeClient = incomeClient;
        this.userClient = userClient;
    }

    @Override
    public Flux<Income> getAllByTelegramUserId(Integer telegramId) {
        Objects.requireNonNull(telegramId, "telegramId is null.");
        return userClient.getByTelegramId(telegramId)
                .map(BotUser::getId)
                .flatMapMany(incomeClient::findAll);
    }

    @Override
    public Mono<Income> create(Income income) {
        return userClient.resolveUser(income.getUser())
                .flatMap(user -> {
                    income.setDate(LocalDate.now());
                    income.setUser(user);
                    return incomeClient.create(income);
                });
    }
}
