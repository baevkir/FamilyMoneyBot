package com.familymoney.telegrambot.business.service;

import com.familymoney.telegrambot.business.mapper.AccountMapper;
import com.familymoney.telegrambot.business.model.Account;
import com.familymoney.telegrambot.persistence.repository.PaymentTypeRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
public class AccountServiceImpl implements AccountService {
    private PaymentTypeRepository paymentTypeRepository;
    private AccountMapper paymentTypeMapper;

    public AccountServiceImpl(PaymentTypeRepository paymentTypeRepository, AccountMapper paymentTypeMapper) {
        this.paymentTypeRepository = paymentTypeRepository;
        this.paymentTypeMapper = paymentTypeMapper;
    }

    @Override
    public Flux<Account> getAll(Long chatId) {
        return paymentTypeRepository.findAllByChatId(chatId).map(paymentTypeMapper::fromEntity);
    }

    @Override
    public Mono<Account> get(Long id) {
        return paymentTypeRepository.findById(id).map(paymentTypeMapper::fromEntity);
    }

    @Override
    public Mono<Account> create(Account paymentType) {
        Objects.requireNonNull(paymentType.getChatId(), "Payment type chat id should be not null.");
        Objects.requireNonNull(paymentType.getName(), "Payment type name should be not null.");
        return Mono.fromSupplier(() -> paymentTypeMapper.toEntity(paymentType))
                .flatMap(paymentTypeRepository::save)
                .map(paymentTypeMapper::fromEntity);
    }

    @Override
    public Mono<Account> resolve(Account paymentType) {
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
