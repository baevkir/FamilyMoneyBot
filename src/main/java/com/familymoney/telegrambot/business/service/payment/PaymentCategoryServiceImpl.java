package com.familymoney.telegrambot.business.service.payment;

import com.familymoney.telegrambot.business.mapper.PaymentCategoryMapper;
import com.familymoney.telegrambot.business.mapper.PaymentTypeMapper;
import com.familymoney.telegrambot.business.model.PaymentCategory;
import com.familymoney.telegrambot.business.model.PaymentType;
import com.familymoney.telegrambot.persistence.repository.PaymentCategoryRepository;
import com.familymoney.telegrambot.persistence.repository.PaymentTypeRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
public class PaymentCategoryServiceImpl implements PaymentCategoryService {
    private PaymentCategoryRepository paymentCategoryRepository;
    private PaymentCategoryMapper paymentCategoryMapper;

    public PaymentCategoryServiceImpl(PaymentCategoryRepository paymentCategoryRepository, PaymentCategoryMapper paymentCategoryMapper) {
        this.paymentCategoryRepository = paymentCategoryRepository;
        this.paymentCategoryMapper = paymentCategoryMapper;
    }

    @Override
    public Flux<PaymentCategory> getAll(Long chatId) {
        return paymentCategoryRepository.findAllByChatId(chatId).map(paymentCategoryMapper::fromEntity);
    }

    @Override
    public Mono<PaymentCategory> create(PaymentCategory paymentCategory) {
        Objects.requireNonNull(paymentCategory.getChatId(), "Payment category chat id should be not null.");
        Objects.requireNonNull(paymentCategory.getName(), "Payment category name should be not null.");
        return Mono.fromSupplier(() -> paymentCategoryMapper.toEntity(paymentCategory))
                .flatMap(paymentCategoryRepository::save)
                .map(paymentCategoryMapper::fromEntity);
    }

    @Override
    public Mono<PaymentCategory> resolvePaymentType(PaymentCategory paymentCategory) {
        if (paymentCategory.getId() != null) {
            return paymentCategoryRepository.findById(paymentCategory.getId())
                    .map(paymentCategoryMapper::fromEntity);
        }
        Objects.requireNonNull(paymentCategory.getName(), "Payment category name should be not null.");
        return paymentCategoryRepository.findByName(paymentCategory.getName())
                .map(paymentCategoryMapper::fromEntity)
                .switchIfEmpty(create(paymentCategory));
    }
}
