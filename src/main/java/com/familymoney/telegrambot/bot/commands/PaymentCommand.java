package com.familymoney.telegrambot.bot.commands;

import com.familymoney.telegrambot.bot.commands.annotations.CommandMethod;
import com.familymoney.telegrambot.bot.commands.annotations.Param;
import com.familymoney.telegrambot.bot.errors.exception.validation.DateValidationException;
import com.familymoney.telegrambot.bot.errors.exception.validation.PaymentCategoryValidationException;
import com.familymoney.telegrambot.bot.errors.exception.validation.AccountValidationException;
import com.familymoney.telegrambot.business.mapper.UserMapper;
import com.familymoney.telegrambot.business.model.Account;
import com.familymoney.telegrambot.business.model.PaymentCategory;
import com.familymoney.telegrambot.business.service.payment.PaymentService;
import com.familymoney.telegrambot.business.model.Payment;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;

@Slf4j
@Component
public class PaymentCommand extends ReactiveBotCommand {

    private PaymentService paymentService;
    private UserMapper userMapper;

    @Autowired
    public PaymentCommand(PaymentService paymentService, UserMapper userMapper) {
        super("payment", "Создать расход в системе.");
        this.paymentService = paymentService;
        this.userMapper = userMapper;
    }

    @CommandMethod
    public Mono<? extends BotApiMethod<?>> process(
            Message command,
            @Param(index = 0, displayName = "Дата расхода", errorType = DateValidationException.class) LocalDate date,
            @Param(index = 1, displayName = "Вид оплаты", errorType = AccountValidationException.class) String type,
            @Param(index = 2, displayName = "Категорию", errorType = PaymentCategoryValidationException.class) String category,
            @Param(index = 3, displayName = "Сумма") BigDecimal amount) {

        Payment paymentDto = new Payment();
        paymentDto.setChatId(command.getChatId());
        paymentDto.setDate(date);
        paymentDto.setUser(userMapper.fromTelegramPojo(command.getFrom()));
        paymentDto.setAccount(Account.builder()
                .chatId(command.getChatId())
                .name(type)
                .build());
        paymentDto.setCategory(PaymentCategory.builder()
            .chatId(command.getChatId())
            .name(category)
            .build());
        paymentDto.setAmount(amount);

        return paymentService.create(paymentDto)
                .map(result -> new SendMessage(
                        command.getChatId(),
                        String.format("Платеж пользователя %s успешно сохранен.", paymentDto.getUser().getUserName()))
                );

    }


}
