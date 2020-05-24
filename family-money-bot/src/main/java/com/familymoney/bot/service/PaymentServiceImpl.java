package com.familymoney.bot.service;

import com.familymoney.bot.client.AccountClient;
import com.familymoney.bot.client.PaymentCategoryClient;
import com.familymoney.bot.client.PaymentClient;
import com.familymoney.bot.client.UserClient;
import com.familymoney.model.Account;
import com.familymoney.model.BotUser;
import com.familymoney.model.Payment;
import com.familymoney.model.PaymentCategory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple3;
import reactor.util.function.Tuples;

import java.util.Objects;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

    private PaymentClient paymentClient;
    private UserClient userClient;
    private AccountClient accountClient;
    private PaymentCategoryClient paymentCategoryClient;

    public PaymentServiceImpl(PaymentClient paymentClient, UserClient userClient, AccountClient accountClient, PaymentCategoryClient paymentCategoryClient) {
        this.paymentClient = paymentClient;
        this.userClient = userClient;
        this.accountClient = accountClient;
        this.paymentCategoryClient = paymentCategoryClient;
    }

    @Override
    public Flux<Payment> getAllByTelegramUserId(Integer telegramId) {
        Objects.requireNonNull(telegramId, "telegramId is null.");
        return userClient.getByTelegramId(telegramId)
                .flatMapMany(user -> accountClient.getAllIds(user.getId()))
                .collectList()
                .flatMapMany(paymentClient::findAllByAccountIds);
    }

    @Override
    public Mono<Payment> create(Payment payment) {
        return prepareDataForPayment(payment).flatMap(data -> {
            payment.setUser(data.getT1());
            payment.setAccount(data.getT2());
            payment.setCategory(data.getT3());

            return paymentClient.create(payment);
        });
    }

    private Mono<Tuple3<BotUser, Account, PaymentCategory>> prepareDataForPayment(Payment payment) {
        return userClient.resolveUser(payment.getUser()).flatMap(user -> {
            payment.getAccount().setUserId(user.getId());
            payment.getCategory().setUserId(user.getId());
            return Mono.zip(
                    accountClient.resolveAccount(payment.getAccount()),
                    paymentCategoryClient.resolvePaymentCategory(payment.getCategory()))
                    .map(tuple -> Tuples.of(user, tuple.getT1(), tuple.getT2()));
        });
    }

}
