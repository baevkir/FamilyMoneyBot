package com.familymoney.transaction.bussines.service;

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

    public IncomeServiceImpl(IncomeRepository incomeRepository, IncomeMapper incomeMapper) {
        this.incomeRepository = incomeRepository;
        this.incomeMapper = incomeMapper;
    }

    @Override
    public Flux<Income> getAllByAccountIds(Long... accountIds) {
        Objects.requireNonNull(accountIds, "accountIds is null.");
        return Flux.fromArray(accountIds)
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
        return incomeRepository.save(incomeMapper.toEntity(income))
                .map(incomeMapper::fromEntity);

    }

}
