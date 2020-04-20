package com.familymoney.telegrambot.business.service;

import com.familymoney.telegrambot.business.model.PaymentType;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Component
public class PaymentTypeServiceImpl implements PaymentTypeService {

    private Map<Long, PaymentType> paymentTypeMap = new HashMap<>();

    @Override
    public Flux<PaymentType> getAll(Long chatId) {
        return Flux.fromIterable(paymentTypeMap.values())
                .filter(paymentType -> paymentType.getChatId().equals(chatId));
    }

    @Override
    public Mono<PaymentType> create(PaymentType paymentType) {
        paymentType.setId(System.currentTimeMillis());
        return Mono.fromSupplier(() -> {
            paymentTypeMap.put(paymentType.getId(), paymentType);
            return paymentType;
        });

    }
}
