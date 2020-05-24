package com.familymoney.transaction.web.controller;

import com.familymoney.model.Payment;
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
@RequestMapping("family-money/v1/transactions/payments")
public class PaymentController {

    private PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public Flux<Payment> getAllByAccountIds(@RequestParam Long... accountIds) {
        return paymentService.getAllByAccountIds(accountIds);
    }

    @PostMapping
    public Mono<Payment> create(@RequestBody Payment payment) {
        return paymentService.create(payment);
    }
}
