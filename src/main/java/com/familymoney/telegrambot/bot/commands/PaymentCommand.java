package com.familymoney.telegrambot.bot.commands;

import com.familymoney.telegrambot.bot.cash.CommandsCash;
import com.familymoney.telegrambot.bot.commands.annotations.CommandMethod;
import com.familymoney.telegrambot.bot.commands.annotations.Param;
import com.familymoney.telegrambot.bot.errors.PaymentTypeInputException;
import com.familymoney.telegrambot.business.service.PaymentService;
import com.familymoney.telegrambot.business.service.UserService;
import com.familymoney.telegrambot.business.model.Payment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Component
public class PaymentCommand extends ReactiveBotCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(HelpCommand.class);

    private PaymentService paymentService;
    private UserService userService;
    private CommandsCash commandsCash;

    @Autowired
    public PaymentCommand(PaymentService paymentService, UserService userService, CommandsCash commandsCash) {
        super("payment", "Создать платеж в системе.");
        this.paymentService = paymentService;
        this.userService = userService;
        this.commandsCash = commandsCash;
    }

    @CommandMethod
    public Mono<? extends BotApiMethod<?>> process(
            Message command,
            @Param(index = 0, displayName = "Вид оплаты", errorType = PaymentTypeInputException.class) String type,
            @Param(index = 1, displayName = "Сумма") BigDecimal amount) {
        return userService.resolveUser(command.getFrom()).flatMap(botUser -> {
            Payment paymentDto = new Payment();
            paymentDto.setChatId(command.getChatId());
            paymentDto.setUser(botUser);
            paymentDto.setType(type);
            paymentDto.setAmount(amount);

            return paymentService.create(paymentDto)
                    .map(result -> new SendMessage(command.getChatId(), String.format("Палтеж для пользователя %s успешно сохранен.", botUser.getUserName())));
        });
    }


}
