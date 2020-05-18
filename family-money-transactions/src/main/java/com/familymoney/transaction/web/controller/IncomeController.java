package com.familymoney.transaction.web.controller;

import com.familymoney.model.Income;
import com.familymoney.model.Payment;
import com.familymoney.transaction.bussines.service.IncomeService;
import com.familymoney.transaction.bussines.service.PaymentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("family-money/v1/incomes")
public class IncomeController {

    private IncomeService incomeService;

    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @GetMapping
    public Flux<Income> getAllByAccountIds(@RequestParam Long... accountIds) {
        return incomeService.getAllByAccountIds(accountIds);
    }

    @PostMapping
    public Mono<Income> create(@RequestBody Income income) {
        return incomeService.create(income);
    }
}
