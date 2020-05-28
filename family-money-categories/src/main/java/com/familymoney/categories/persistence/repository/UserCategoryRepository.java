package com.familymoney.categories.persistence.repository;

import com.familymoney.categories.persistence.entity.UserCategoryEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface UserCategoryRepository extends ReactiveCrudRepository<UserCategoryEntity, Long> {
    Flux<UserCategoryEntity> getAllByUserId(Long userId);
}
