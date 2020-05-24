package com.familymoney.users.web.controller;

import com.familymoney.model.BotUser;
import com.familymoney.model.PaymentCategory;
import com.familymoney.users.bussines.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("family-money/v1/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(params = "telegramId")
    public Mono<BotUser> findUser(@RequestParam Long telegramId) {
        return userService.getByTelegramId(telegramId);
    }

    @GetMapping(params = "userName")
    public Mono<BotUser> findUser(@RequestParam String userName) {
        return userService.getByUserName(userName);
    }

    @PutMapping(value = "resolve")
    public Mono<BotUser> resolve(@RequestBody BotUser paymentCategory) {
        return userService.resolve(paymentCategory);
    }
}
