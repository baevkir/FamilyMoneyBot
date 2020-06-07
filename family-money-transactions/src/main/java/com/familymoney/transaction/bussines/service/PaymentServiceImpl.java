package com.familymoney.transaction.bussines.service;

import com.familymoney.clients.AccountClient;
import com.familymoney.model.Payment;
import com.familymoney.transaction.bussines.mapper.PaymentMapper;
import com.familymoney.transaction.persistence.repository.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Objects;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

    private PaymentRepository paymentRepository;
    private PaymentMapper paymentMapper;
    private AccountClient accountClient;

    public PaymentServiceImpl(PaymentRepository paymentRepository, PaymentMapper paymentMapper, AccountClient accountClient) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
        this.accountClient = accountClient;
    }

    @Override
    public Flux<Payment> getAll(Long userId) {
        Objects.requireNonNull(userId, "userId is null.");
        return accountClient.getAllIds(userId)
                .flatMap(paymentRepository::findAllByAccountId)
                .map(paymentMapper::fromEntity);
    }

    @Override
    @Transactional
    public Mono<Payment> create(Payment payment) {
        Objects.requireNonNull(payment, "payment is null.");
        Objects.requireNonNull(payment.getUser(), "user is null.");
        Objects.requireNonNull(payment.getAccount(), "account is null.");
        Objects.requireNonNull(payment.getAmount(), "amount is null.");

        if (payment.getDate() == null) {
            payment.setDate(LocalDate.now());
        }
        return paymentRepository.save(paymentMapper.toEntity(payment))
                .map(paymentMapper::fromEntity);
    }

}
