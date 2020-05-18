package com.familymoney.transaction.bussines.service;

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

    public PaymentServiceImpl(PaymentRepository paymentRepository, PaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
    }

    @Override
    public Flux<Payment> getAllByAccountIds(Long... accountIds) {
        Objects.requireNonNull(accountIds, "accountIds is null.");
        return Flux.fromArray(accountIds)
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
