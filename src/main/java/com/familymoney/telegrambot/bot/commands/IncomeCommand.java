package com.familymoney.telegrambot.bot.commands;

import com.familymoney.telegrambot.bot.commands.annotations.CommandMethod;
import com.familymoney.telegrambot.bot.commands.annotations.Param;
import com.familymoney.telegrambot.bot.errors.AccountValidationException;
import com.familymoney.telegrambot.business.mapper.UserMapper;
import com.familymoney.telegrambot.business.model.Account;
import com.familymoney.telegrambot.business.model.Income;
import com.familymoney.telegrambot.business.model.Payment;
import com.familymoney.telegrambot.business.model.PaymentCategory;
import com.familymoney.telegrambot.business.service.IncomeService;
import com.familymoney.telegrambot.business.service.payment.PaymentService;
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

@Slf4j
@Component
public class IncomeCommand extends ReactiveBotCommand {

    private IncomeService incomeService;
    private UserMapper userMapper;

    @Autowired
    public IncomeCommand(IncomeService incomeService, UserMapper userMapper) {
        super("income", "Создать доходный документ в системе.");
        this.incomeService = incomeService;
        this.userMapper = userMapper;
    }

    @CommandMethod
    public Mono<? extends BotApiMethod<?>> process(
            Message command,
            @Param(index = 0, displayName = "Вид оплаты", errorType = AccountValidationException.class) String type,
            @Param(index = 2, displayName = "Сумма") BigDecimal amount) {

        Income income = new Income();
        income.setChatId(command.getChatId());
        income.setUser(userMapper.fromTelegramPojo(command.getFrom()));
        income.setAccount(Account.builder()
                .chatId(command.getChatId())
                .name(type)
                .build());
        income.setAmount(amount);

        return incomeService.create(income)
                .map(result -> new SendMessage(
                        command.getChatId(),
                        String.format("Платеж пользователя %s успешно сохранен.", income.getUser().getUserName()))
                );

    }


}
