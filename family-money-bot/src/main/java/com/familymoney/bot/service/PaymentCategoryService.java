package com.familymoney.bot.service;

import com.familymoney.model.PaymentCategory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PaymentCategoryService {
    Flux<PaymentCategory> getAllForTelegramUserId(Integer telegramId);
    Mono<Void> shareForUser(Integer sourceTelegramId, String targetUserName);
}
