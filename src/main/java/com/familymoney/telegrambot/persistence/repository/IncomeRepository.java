package com.familymoney.telegrambot.persistence.repository;

import com.familymoney.telegrambot.persistence.entity.IncomeEntity;
import org.reactivestreams.Publisher;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.util.List;

public interface IncomeRepository extends R2dbcRepository<IncomeEntity, Long> {
    Flux<IncomeEntity> findAllByAccountIdIn(List<Long> accountIds);
}
