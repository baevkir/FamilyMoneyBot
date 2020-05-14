package com.familymoney.telegrambot.bot.commands;

import com.familymoney.telegrambot.bot.errors.AccountValidationException;
import com.familymoney.telegrambot.bot.errors.PaymentCategoryValidationException;
import com.familymoney.telegrambot.business.mapper.UserMapper;
import com.familymoney.telegrambot.business.model.Account;
import com.familymoney.telegrambot.business.model.Payment;
import com.familymoney.telegrambot.business.model.PaymentCategory;
import com.familymoney.telegrambot.business.service.payment.PaymentService;
import com.sessionbot.commands.ReactiveBotCommand;
import com.sessionbot.commands.annotations.CommandMethod;
import com.sessionbot.commands.annotations.Param;
import com.sessionbot.errors.exception.validation.DateValidationException;
import lombok.extern.slf4j.Slf4j;
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
        paymentDto.setDate(date);
        paymentDto.setUser(userMapper.fromTelegramPojo(command.getFrom()));
        paymentDto.setAccount(Account.builder()
                .name(type)
                .build());
        paymentDto.setCategory(PaymentCategory.builder()
            .name(category)
            .build());
        paymentDto.setAmount(amount);

        return paymentService.create(paymentDto)
                .map(result -> new SendMessage(
                        command.getChatId(),
                        String.format("Платеж пользователя %s успешно сохранен.", paymentDto.getUser().getFullName()))
                );

    }


}
