package com.familymoney.transaction.bussines.service;

import com.familymoney.clients.AccountClient;
import com.familymoney.model.Account;
import com.familymoney.model.Income;
import com.familymoney.transaction.bussines.mapper.IncomeMapper;
import com.familymoney.transaction.persistence.repository.IncomeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Objects;

@Slf4j
@Service
public class IncomeServiceImpl implements IncomeService {
    private IncomeRepository incomeRepository;
    private IncomeMapper incomeMapper;
    private AccountClient accountClient;

    public IncomeServiceImpl(IncomeRepository incomeRepository, IncomeMapper incomeMapper, AccountClient accountClient) {
        this.incomeRepository = incomeRepository;
        this.incomeMapper = incomeMapper;
        this.accountClient = accountClient;
    }

    @Override
    public Flux<Income> getAll(Long userId) {
        Objects.requireNonNull(userId, "userId is null.");
        return accountClient.getAllIds(userId)
                .flatMap(incomeRepository::findAllByAccountId)
                .map(incomeMapper::fromEntity);
    }

    @Override
    @Transactional
    public Mono<Income> create(Income income) {
        Objects.requireNonNull(income, "income is null.");
        Objects.requireNonNull(income.getUser(), "user is null.");
        Objects.requireNonNull(income.getAccount(), "account is null.");
        Objects.requireNonNull(income.getAmount(), "amount is null.");

        if (income.getDate() == null) {
            income.setDate(LocalDate.now());
        }
        Account account = income.getAccount();
        account.setUserId(income.getUser().getId());
        return accountClient.resolveAccount(account)
                .flatMap(result -> {
                    income.setAccount(result);
                    return incomeRepository.save(incomeMapper.toEntity(income));
                })
                .map(incomeMapper::fromEntity);
    }

}

