package com.familymoney.telegrambot.persistence.repository.account;

import com.familymoney.telegrambot.persistence.entity.account.AccountEntity;
import org.reactivestreams.Publisher;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;

public interface AccountRepository extends R2dbcRepository<AccountEntity, Long> {
}
