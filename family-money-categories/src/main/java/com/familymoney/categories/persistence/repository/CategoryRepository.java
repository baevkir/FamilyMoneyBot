package com.familymoney.categories.persistence.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import com.familymoney.categories.persistence.entity.CategoryEntity;

public interface CategoryRepository extends ReactiveCrudRepository<CategoryEntity, Long> {
}
