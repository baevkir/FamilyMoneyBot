package com.familymoney.categories.web.controller;

import com.familymoney.categories.bussines.service.CategoryService;
import com.familymoney.model.Account;
import com.familymoney.model.PaymentCategory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("family-money/v1/categories")
public class CategoriesController {

    private CategoryService categoryService;

    public CategoriesController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping()
    public Flux<PaymentCategory> getAll(@RequestParam Long userId) {
        return categoryService.getAll(userId);
    }

    @PutMapping(value = "resolve")
    public Mono<PaymentCategory> resolve(@RequestBody PaymentCategory paymentCategory) {
        return categoryService.resolve(paymentCategory);
    }

}
