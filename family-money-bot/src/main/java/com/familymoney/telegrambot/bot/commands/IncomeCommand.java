package com.familymoney.telegrambot.bot.commands;

import com.familymoney.model.Account;
import com.familymoney.model.Income;
import com.familymoney.telegrambot.bot.errors.AccountValidationException;
import com.familymoney.telegrambot.business.mapper.UserMapper;
import com.familymoney.telegrambot.business.service.IncomeService;
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
            @Param(index = 0, displayName = "Дата поступления", errorType = DateValidationException.class) LocalDate date,
            @Param(index = 1, displayName = "Вид оплаты", errorType = AccountValidationException.class) String type,
            @Param(index = 2, displayName = "Сумма") BigDecimal amount) {

        Income income = new Income();
        income.setUser(userMapper.fromTelegramPojo(command.getFrom()));
        income.setAccount(Account.builder()
                .name(type)
                .build());
        income.setAmount(amount);

        return incomeService.create(income)
                .map(result -> new SendMessage(
                        command.getChatId(),
                        String.format("Платеж пользователя %s успешно сохранен.", income.getUser().getFullName()))
                );

    }


}
