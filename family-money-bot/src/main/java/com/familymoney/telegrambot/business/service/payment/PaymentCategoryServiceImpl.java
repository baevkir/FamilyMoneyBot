package com.familymoney.telegrambot.business.service.payment;

import com.familymoney.model.BotUser;
import com.familymoney.model.PaymentCategory;
import com.familymoney.telegrambot.business.mapper.PaymentCategoryMapper;
import com.familymoney.telegrambot.business.service.UserService;
import com.familymoney.telegrambot.persistence.entity.category.UserPaymentCategoryEntity;
import com.familymoney.telegrambot.persistence.repository.category.PaymentCategoryRepository;
import com.familymoney.telegrambot.persistence.repository.category.UserPaymentCategoryRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class PaymentCategoryServiceImpl implements PaymentCategoryService {
    private PaymentCategoryRepository paymentCategoryRepository;
    private UserPaymentCategoryRepository userPaymentCategoryRepository;
    private PaymentCategoryMapper paymentCategoryMapper;
    private UserService userService;

    public PaymentCategoryServiceImpl(PaymentCategoryRepository paymentCategoryRepository,
                                      UserPaymentCategoryRepository userPaymentCategoryRepository,
                                      PaymentCategoryMapper paymentCategoryMapper,
                                      UserService userService) {
        this.paymentCategoryRepository = paymentCategoryRepository;
        this.userPaymentCategoryRepository = userPaymentCategoryRepository;
        this.paymentCategoryMapper = paymentCategoryMapper;
        this.userService = userService;
    }

    @Override
    public Flux<PaymentCategory> getAll(Long userId) {
        Objects.requireNonNull(userId, "UserId should be not null.");
        return paymentCategoryRepository.findAllById(getAllIds(userId)).map(paymentCategoryMapper::fromEntity);
    }

    @Override
    public Flux<PaymentCategory> getAllForTelegramUserId(Integer telegramId) {
        Objects.requireNonNull(telegramId, "telegramId should be not null.");
        return userService.getByTelegramId(telegramId).flatMapMany(user -> getAll(user.getId()));
    }

    @Override
    public Flux<Long> getAllIds(Long userId) {
        return userPaymentCategoryRepository.findAllByUserId(userId).map(UserPaymentCategoryEntity::getPaymentCategoryId);
    }

    @Override
    public Mono<PaymentCategory> get(Long id) {
        Objects.requireNonNull(id, "Payment category id should be not null.");
        return paymentCategoryRepository.findById(id).map(paymentCategoryMapper::fromEntity);
    }

    @Override
    public Mono<PaymentCategory> find(Long userId, String name) {
        Objects.requireNonNull(name, "Payment category name should be not null.");
        Objects.requireNonNull(userId, "UserId should be not null.");
        return paymentCategoryRepository.findAllById(getAllIds(userId))
                .filter(entity -> entity.getName().equals(name))
                .next()
                .map(category -> paymentCategoryMapper.fromEntity(category, userId));
    }

    @Override
    @Transactional
    public Mono<PaymentCategory> create(PaymentCategory paymentCategory) {
        Objects.requireNonNull(paymentCategory.getName(), "Payment category name should be not null.");
        Objects.requireNonNull(paymentCategory.getUserId(), "User Id should be not null.");
        return Mono.fromSupplier(() -> paymentCategoryMapper.toEntity(paymentCategory))
                .flatMap(paymentCategoryRepository::save)
                .flatMap(entity ->
                        userPaymentCategoryRepository.save(UserPaymentCategoryEntity.builder()
                                .userId(paymentCategory.getUserId())
                                .paymentCategoryId(entity.getId())
                                .build()).thenReturn(entity)
                )
                .map(category -> paymentCategoryMapper.fromEntity(category, paymentCategory.getUserId()));
    }

    @Override
    public Mono<PaymentCategory> resolve(PaymentCategory paymentCategory) {
        if (paymentCategory.getId() != null) {
            return get(paymentCategory.getId());
        }
        return find(paymentCategory.getUserId(), paymentCategory.getName())
                .switchIfEmpty(create(paymentCategory));
    }

    @Override
    public Mono<Void> shareForUser(Integer sourceTelegramId, String targetUserName) {
        return userService.getByUserName(targetUserName)
                .switchIfEmpty(Mono.error(() -> new RuntimeException(String.format("Пользователь %s не найден", targetUserName))))
                .zipWith(userService.getByTelegramId(sourceTelegramId))
                .flatMapMany(tuple -> getCategoriesToShare(tuple.getT1(), tuple.getT2()))
                .collectList()
                .flatMapMany(entities -> userPaymentCategoryRepository.saveAll(entities))
                .then();
    }

    private Flux<UserPaymentCategoryEntity> getCategoriesToShare(BotUser targetUser, BotUser sourceUser) {
        return getAllIds(targetUser.getId())
                .collect(Collectors.toSet())
                .flatMapMany(targetCategories -> getAllIds(sourceUser.getId()).filter(id -> !targetCategories.contains(id)))
                .map(id ->
                        UserPaymentCategoryEntity.builder()
                                .userId(targetUser.getId())
                                .paymentCategoryId(id)
                                .build()
                );
    }

}
