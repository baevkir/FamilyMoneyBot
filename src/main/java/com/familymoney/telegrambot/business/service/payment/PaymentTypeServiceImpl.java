package com.familymoney.telegrambot.business.service.payment;

import com.familymoney.telegrambot.business.mapper.PaymentTypeMapper;
import com.familymoney.telegrambot.business.model.PaymentType;
import com.familymoney.telegrambot.persistence.repository.PaymentTypeRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
public class PaymentTypeServiceImpl implements PaymentTypeService {
    private PaymentTypeRepository paymentTypeRepository;
    private PaymentTypeMapper paymentTypeMapper;

    public PaymentTypeServiceImpl(PaymentTypeRepository paymentTypeRepository, PaymentTypeMapper paymentTypeMapper) {
        this.paymentTypeRepository = paymentTypeRepository;
        this.paymentTypeMapper = paymentTypeMapper;
    }

    @Override
    public Flux<PaymentType> getAll(Long chatId) {
        return paymentTypeRepository.findAllByChatId(chatId).map(paymentTypeMapper::fromEntity);
    }

    @Override
    public Mono<PaymentType> get(Long id) {
        return paymentTypeRepository.findById(id).map(paymentTypeMapper::fromEntity);
    }

    @Override
    public Mono<PaymentType> create(PaymentType paymentType) {
        Objects.requireNonNull(paymentType.getChatId(), "Payment type chat id should be not null.");
        Objects.requireNonNull(paymentType.getName(), "Payment type name should be not null.");
        return Mono.fromSupplier(() -> paymentTypeMapper.toEntity(paymentType))
                .flatMap(paymentTypeRepository::save)
                .map(paymentTypeMapper::fromEntity);
    }

    @Override
    public Mono<PaymentType> resolve(PaymentType paymentType) {
        if (paymentType.getId() != null) {
            return paymentTypeRepository.findById(paymentType.getId())
                    .map(paymentTypeMapper::fromEntity);
        }
        Objects.requireNonNull(paymentType.getName(), "Payment type name should be not null.");
        return paymentTypeRepository.findByName(paymentType.getName())
                .map(paymentTypeMapper::fromEntity)
                .switchIfEmpty(create(paymentType));
    }
}
