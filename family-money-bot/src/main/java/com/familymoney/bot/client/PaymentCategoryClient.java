package com.familymoney.bot.client;

import com.familymoney.model.Account;
import com.familymoney.model.PaymentCategory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class PaymentCategoryClient {
    private static  final String BASE_URL = "http://family-money-categories/family-money/v1/categoties";
    private WebClient.Builder webClientBuilder;

    public PaymentCategoryClient(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public Flux<PaymentCategory> getAll(Long userId) {
        return webClientBuilder.build()
                .get()
                .uri(BASE_URL)
                .retrieve()
                .bodyToFlux(PaymentCategory.class);
    }

    public Mono<PaymentCategory> resolvePaymentCategory(PaymentCategory paymentCategory){
        return webClientBuilder.build()
                .post()
                .uri(BASE_URL + "/resolve")
                .bodyValue(paymentCategory)
                .retrieve()
                .bodyToMono(PaymentCategory.class);
    }
}
