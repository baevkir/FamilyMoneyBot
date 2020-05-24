package com.familymoney.bot.client;

import com.familymoney.model.Income;
import com.familymoney.model.Payment;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class PaymentClient {
    private static  final String BASE_URL = "http://family-money-transactions/family-money/v1/transactions/incomes";
    private WebClient.Builder webClientBuilder;

    public PaymentClient(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public Flux<Payment> findAllByAccountIds(List<Long> accountIds) {
        return webClientBuilder.build()
                .get()
                .uri(BASE_URL + "?accountIds=" + accountIds)
                .retrieve()
                .bodyToFlux(Payment.class);
    }

    public Mono<Payment> create(Payment payment) {
        return webClientBuilder.build()
                .post()
                .uri(BASE_URL)
                .bodyValue(payment)
                .retrieve()
                .bodyToMono(Payment.class);
    }
}
