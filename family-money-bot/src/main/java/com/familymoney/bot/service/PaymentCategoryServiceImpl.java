package com.familymoney.bot.service;

import com.familymoney.bot.client.PaymentCategoryClient;
import com.familymoney.bot.client.UserClient;
import com.familymoney.model.PaymentCategory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Slf4j
@Component
public class PaymentCategoryServiceImpl implements PaymentCategoryService {
    private PaymentCategoryClient paymentCategoryClient;
    private UserClient userClient;

    public PaymentCategoryServiceImpl(PaymentCategoryClient paymentCategoryClient, UserClient userClient) {
        this.paymentCategoryClient = paymentCategoryClient;
        this.userClient = userClient;
    }

    @Override
    public Flux<PaymentCategory> getAllForTelegramUserId(Integer telegramId) {
        Objects.requireNonNull(telegramId, "telegramId should be not null.");
        return userClient.getByTelegramId(telegramId).flatMapMany(user -> paymentCategoryClient.getAll(user.getId()));
    }

    @Override
    public Mono<Void> shareForUser(Integer sourceTelegramId, String targetUserName) {
        log.info("Share for user {} form {}", sourceTelegramId, targetUserName);
        return Mono.empty();
    }

}
