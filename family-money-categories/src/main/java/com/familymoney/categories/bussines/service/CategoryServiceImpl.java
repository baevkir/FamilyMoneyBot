package com.familymoney.categories.bussines.service;

import com.familymoney.categories.bussines.mapper.CategoryMapper;
import com.familymoney.categories.persistence.entity.CategoryEntity;
import com.familymoney.categories.persistence.entity.UserCategoryEntity;
import com.familymoney.categories.persistence.repository.CategoryRepository;
import com.familymoney.categories.persistence.repository.UserCategoryRepository;
import com.familymoney.model.PaymentCategory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;


@Component
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;
    private UserCategoryRepository userCategoryRepository;
    private CategoryMapper categoryMapper;

    public CategoryServiceImpl(
            CategoryRepository categoryRepository,
            CategoryMapper categoryMapper
    ) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public Flux<PaymentCategory> getAll(Long userId) {
        Objects.requireNonNull(userId, "UserId should be not null.");
        return categoryRepository.findAllById(getAllIds(userId))
                .map(account -> categoryMapper.fromEntity(account, userId));
    }

    @Override
    public Flux<Long> getAllIds(Long userId) {
        Objects.requireNonNull(userId, "UserId should be not null.");
        return userCategoryRepository.getAllByUserId(userId).map(UserCategoryEntity::getCategoryId);
    }

    @Override
    public Mono<PaymentCategory> get(Long id) {
        Objects.requireNonNull(id, "Id should be not null.");
        return categoryRepository.findById(id).map(categoryMapper::fromEntity);
    }

    @Override
    public Mono<PaymentCategory> find(Long userId, String name) {
        Objects.requireNonNull(userId, "Payment category name should be not null.");
        Objects.requireNonNull(name, "UserId should be not null.");
        return categoryRepository.findAllById(getAllIds(userId))
                .filter(accountEntity -> accountEntity.getName().equals(name))
                .next()
                .map(entity -> categoryMapper.fromEntity(entity, userId));
    }

    @Override
    @Transactional
    public Mono<PaymentCategory> create(PaymentCategory paymentCategory) {
        Objects.requireNonNull(paymentCategory.getName(), "Payment category name should be not null.");
        Objects.requireNonNull(paymentCategory.getUserId(), "UserId should be not null.");
        return Mono.fromSupplier(() -> categoryMapper.toEntity(paymentCategory))
                .flatMap(categoryRepository::save)
                .map(categoryMapper::fromEntity);
    }

    @Override
    public Mono<PaymentCategory> resolve(PaymentCategory paymentCategory) {
        if (paymentCategory.getId() != null) {
            return get(paymentCategory.getId());
        }
        return find(paymentCategory.getUserId(), paymentCategory.getName())
                .switchIfEmpty(create(paymentCategory));
    }
}
