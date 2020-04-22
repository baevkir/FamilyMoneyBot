package com.familymoney.telegrambot.business.service.payment;

import com.familymoney.telegrambot.business.mapper.PaymentMapper;
import com.familymoney.telegrambot.business.model.BotUser;
import com.familymoney.telegrambot.business.model.Payment;
import com.familymoney.telegrambot.business.model.PaymentCategory;
import com.familymoney.telegrambot.business.model.Account;
import com.familymoney.telegrambot.business.service.AccountService;
import com.familymoney.telegrambot.business.service.UserService;
import com.familymoney.telegrambot.persistence.entity.PaymentEntity;
import com.familymoney.telegrambot.persistence.repository.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple3;

import java.time.LocalDate;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

    private PaymentRepository paymentRepository;
    private PaymentMapper paymentMapper;
    private UserService userService;
    private AccountService accountService;
    private PaymentCategoryService paymentCategoryService;

    public PaymentServiceImpl(PaymentRepository paymentRepository,
                              PaymentMapper paymentMapper,
                              UserService userService,
                              AccountService accountService,
                              PaymentCategoryService paymentCategoryService) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
        this.userService = userService;
        this.accountService = accountService;
        this.paymentCategoryService = paymentCategoryService;
    }

    @Override
    public Flux<Payment> getAll(Long chatId) {
        return paymentRepository.findAllByChatId(chatId).flatMap(entity ->
                prepareDataForPayment(entity).map(data -> paymentMapper.fromEntity(entity, data.getT1(), data.getT2(), data.getT3())));
    }

    @Override
    @Transactional
    public Mono<Payment> create(Payment payment) {
        return prepareDataForPayment(payment).flatMap(data -> {
            payment.setDate(LocalDate.now());
            payment.setUser(data.getT1());
            payment.setAccount(data.getT2());
            payment.setCategory(data.getT3());

            return paymentRepository.save(paymentMapper.toEntity(payment))
                    .map(result -> paymentMapper.fromEntity(result, data.getT1(), data.getT2(), data.getT3()));
        });
    }

    private Mono<Tuple3<BotUser, Account, PaymentCategory>> prepareDataForPayment(Payment payment) {
        return Mono.zip(
                userService.resolve(payment.getUser()),
                accountService.resolve(payment.getAccount()),
                paymentCategoryService.resolve(payment.getCategory())
        );
    }

    private Mono<Tuple3<BotUser, Account, PaymentCategory>> prepareDataForPayment(PaymentEntity payment) {
        return Mono.zip(
                userService.get(payment.getUserId()),
                accountService.get(payment.getAccountId()),
                paymentCategoryService.get(payment.getPaymentCategoryId())
        );
    }
}
