package com.familymoney.categories.persistence.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import com.familymoney.categories.persistence.entity.PaymentCategoryEntity;

public interface CategoryRepository extends ReactiveCrudRepository<PaymentCategoryEntity, Long> {
    Flux<PaymentCategoryEntity> getAllByUserIdsContains(Long userId);
}
