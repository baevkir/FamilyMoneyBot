package com.familymoney.clients;

import com.familymoney.model.BotUser;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class UserClient {
    private static  final String BASE_URL = "http://family-money-users/family-money/v1/users";
    private WebClient.Builder webClientBuilder;

    public UserClient(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public Mono<BotUser> getByTelegramId(Integer telegramId) {
        return webClientBuilder.build()
                .get()
                .uri(BASE_URL + "?telegramId=" + telegramId)
                .retrieve()
                .bodyToMono(BotUser.class);
    }

    public Mono<BotUser> getByUserName(String userName) {
        return webClientBuilder.build()
                .get()
                .uri(BASE_URL + "?userName=" + userName)
                .retrieve()
                .bodyToMono(BotUser.class);
    }

    public Mono<BotUser> resolveUser(BotUser user){
        return webClientBuilder.build()
                .put()
                .uri(BASE_URL + "/resolve")
                .bodyValue(user)
                .retrieve()
                .bodyToMono(BotUser.class);
    }
}
