package com.familymoney.telegrambot.bot.commands;

import com.familymoney.telegrambot.bot.commands.annotations.CommandMethod;
import com.familymoney.telegrambot.business.service.IncomeService;
import com.familymoney.telegrambot.business.service.payment.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import reactor.core.publisher.Mono;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Component
public class GetIncomesCommand extends ReactiveBotCommand {
    private IncomeService incomeService;

    public GetIncomesCommand(IncomeService incomeService) {
        super("incomes", "Получить полный список поступлений.");
        this.incomeService = incomeService;
    }

    @CommandMethod
    public Mono<? extends BotApiMethod<?>> process(Message command) {
        return incomeService.getAll(command.getChatId()).collect(Collectors.toList()).map(payments -> {
            String message = payments.stream()
                    .map(payment -> String.format(
                            "Пользователь: %s Дата: %s Вид Оплаты: %s Сумма: %s",
                            payment.getUser().getUserName(),
                            payment.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                            payment.getAccount().getName(),
                            payment.getAmount()))
                    .collect(Collectors.joining("\n", "Поступления: \n", ""));

            return new SendMessage(command.getChatId(), message);
        });
    }
}
