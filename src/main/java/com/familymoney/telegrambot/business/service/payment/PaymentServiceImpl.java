package com.familymoney.telegrambot.business.service.payment;

import com.familymoney.telegrambot.business.model.Payment;
import com.familymoney.telegrambot.business.model.PaymentCategory;
import com.familymoney.telegrambot.business.model.PaymentType;
import com.familymoney.telegrambot.business.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class PaymentServiceImpl implements PaymentService {
    Map<Long, Payment> payments = new HashMap<>();

    private UserService userService;
    private PaymentTypeService paymentTypeService;
    private PaymentCategoryService paymentCategoryService;

    public PaymentServiceImpl(UserService userService, PaymentTypeService paymentTypeService, PaymentCategoryService paymentCategoryService) {
        this.userService = userService;
        this.paymentTypeService = paymentTypeService;
        this.paymentCategoryService = paymentCategoryService;
    }

    @Override
    public Flux<Payment> getAllPayments(Long chatId) {
        return Flux.fromIterable(payments.values())
                .filter(payment -> Objects.equals(payment.getChatId(), chatId));
    }

    @Override
    @Transactional
    public Mono<Payment> create(Payment payment) {
        return userService.resolveUser(payment.getUser()).flatMap(botUser ->
                paymentTypeService.resolvePaymentType(payment.getType()).flatMap(paymentType ->
                        paymentCategoryService.resolvePaymentType(payment.getCategory())
                                .map(paymentCategory -> {
                                    payment.setId(System.currentTimeMillis());
                                    payment.setPaymentDate(LocalDateTime.now());
                                    payment.setUser(botUser);
                                    payment.setType(paymentType);
                                    payment.setCategory(paymentCategory);
                                    payments.put(payment.getId(), payment);
                                    return payment;
                                })
                ));

    }
}
