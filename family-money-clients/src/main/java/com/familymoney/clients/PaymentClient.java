package com.familymoney.clients;

import com.familymoney.model.Payment;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class PaymentClient {
    private static  final String BASE_URL = "http://family-money-transactions/family-money/v1/users/{userId}/transactions/payments";
    private WebClient.Builder webClientBuilder;

    public PaymentClient(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public Flux<Payment> findAll(Long userId) {
        return webClientBuilder.build()
                .get()
                .uri(BASE_URL, userId)
                .retrieve()
                .bodyToFlux(Payment.class);
    }

    public Mono<Payment> create(Payment payment) {
        return webClientBuilder.build()
                .post()
                .uri(BASE_URL, payment.getUser().getId())
                .bodyValue(payment)
                .retrieve()
                .bodyToMono(Payment.class);
    }
}
