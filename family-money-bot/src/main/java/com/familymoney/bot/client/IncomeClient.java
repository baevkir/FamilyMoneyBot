package com.familymoney.bot.client;

import com.familymoney.model.Income;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class IncomeClient {
    private static  final String BASE_URL = "http://:8080/family-money/v1/users/{userId}/transactions/incomes";
    private WebClient.Builder webClientBuilder;

    public IncomeClient(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public Flux<Income> findAll(Long userId){
        return webClientBuilder.build()
                .get()
                .uri(BASE_URL, userId)
                .retrieve()
                .bodyToFlux(Income.class);
    }

    public Mono<Income> create(Income income){
        return webClientBuilder.build()
                .post()
                .uri(BASE_URL, income.getUser().getId())
                .bodyValue(income)
                .retrieve()
                .bodyToMono(Income.class);
    }
}
