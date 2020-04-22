package com.familymoney.telegrambot.persistence.repository;

import com.familymoney.telegrambot.business.model.Income;
import com.familymoney.telegrambot.persistence.entity.IncomeEntity;
import com.familymoney.telegrambot.persistence.entity.PaymentEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface IncomeRepository extends ReactiveCrudRepository<IncomeEntity, Long> {
    Flux<IncomeEntity> findAllByChatId(Long chatId);
}
