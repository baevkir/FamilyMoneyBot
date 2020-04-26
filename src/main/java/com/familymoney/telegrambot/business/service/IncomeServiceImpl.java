package com.familymoney.telegrambot.business.service;

import com.familymoney.telegrambot.business.mapper.IncomeMapper;
import com.familymoney.telegrambot.business.model.Account;
import com.familymoney.telegrambot.business.model.BotUser;
import com.familymoney.telegrambot.business.model.Income;
import com.familymoney.telegrambot.persistence.entity.IncomeEntity;
import com.familymoney.telegrambot.persistence.repository.IncomeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.time.LocalDate;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class IncomeServiceImpl implements IncomeService {

    private IncomeRepository incomeRepository;
    private IncomeMapper incomeMapper;
    private UserService userService;
    private AccountService accountService;

    public IncomeServiceImpl(IncomeRepository incomeRepository, IncomeMapper incomeMapper, UserService userService, AccountService accountService) {
        this.incomeRepository = incomeRepository;
        this.incomeMapper = incomeMapper;
        this.userService = userService;
        this.accountService = accountService;
    }

    @Override
    public Flux<Income> getAllByTelegramUserId(Integer telegramId) {
        Objects.requireNonNull(telegramId, "telegramId is null.");
        return userService.getByTelegramId(telegramId)
                .flatMapMany(user -> accountService.getAllIds(user.getId()))
                .flatMap(incomeRepository::findAllByAccountId)
                .flatMap(entity -> prepareData(entity)
                        .map(data -> incomeMapper.fromEntity(entity, data.getT1(), data.getT2())));
    }

    @Override
    @Transactional
    public Mono<Income> create(Income income) {
        return prepareData(income).flatMap(data -> {
            income.setDate(LocalDate.now());
            income.setUser(data.getT1());
            income.setAccount(data.getT2());

            return incomeRepository.save(incomeMapper.toEntity(income))
                    .map(result -> incomeMapper.fromEntity(result, data.getT1(), data.getT2()));
        });
    }

    private Mono<Tuple2<BotUser, Account>> prepareData(Income income) {
        return userService.resolve(income.getUser()).flatMap(user -> {
            Account account = income.getAccount();
            account.setUserId(user.getId());
            return accountService.resolve(account).map(result -> Tuples.of(user, result));
        });
    }

    private Mono<Tuple2<BotUser, Account>> prepareData(IncomeEntity income) {
        return Mono.zip(
                userService.get(income.getUserId()),
                accountService.get(income.getAccountId())
        );
    }
}
