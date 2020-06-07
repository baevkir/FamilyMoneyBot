package com.familymoney.clients;

import com.familymoney.model.Account;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class AccountClient {
    private static  final String BASE_URL = "http://family-money-accounts/family-money/v1/users/{userId}/accounts";
    private WebClient.Builder webClientBuilder;

    public AccountClient(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public Flux<Long> getAllIds(Long userId){
        return webClientBuilder.build()
                .get()
                .uri(BASE_URL + "/ids", userId)
                .retrieve()
                .bodyToFlux(Long.class);
    }

    public Flux<Account> getAll(Long userId){
        return webClientBuilder.build()
                .get()
                .uri(BASE_URL, userId)
                .retrieve()
                .bodyToFlux(Account.class);
    }

    public Mono<Account> resolveAccount(Account account){
        return webClientBuilder.build()
                .put()
                .uri(BASE_URL + "/resolve", account.getUserId())
                .bodyValue(account)
                .retrieve()
                .bodyToMono(Account.class);
    }
}
