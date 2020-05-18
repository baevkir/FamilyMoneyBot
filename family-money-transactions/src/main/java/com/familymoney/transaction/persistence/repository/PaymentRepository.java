package com.familymoney.transaction.persistence.repository;

import com.familymoney.transaction.persistence.entity.PaymentEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface PaymentRepository extends R2dbcRepository<PaymentEntity, Long> {
    Flux<PaymentEntity> findAllByAccountId(Long accountId);
}
