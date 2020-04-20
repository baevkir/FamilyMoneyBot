package com.familymoney.telegrambot.business.service;

import com.familymoney.telegrambot.business.model.Payment;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class PaymentServiceImpl implements PaymentService {
    Map<Long, Payment> payments = new HashMap<>();

    @Override
    public Flux<Payment> getAllPayments(Long chatId) {
        return Flux.fromIterable(payments.values())
                .filter(payment -> Objects.equals(payment.getChatId(), chatId));
    }

    @Override
    public Mono<Payment> create(Payment payment) {
        payment.setId(System.currentTimeMillis());
        payment.setPaymentDate(LocalDateTime.now());
        payments.put(payment.getId(), payment);
        return Mono.just(payment);
    }
}
