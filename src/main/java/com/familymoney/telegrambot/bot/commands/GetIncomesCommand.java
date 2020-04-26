package com.familymoney.telegrambot.bot.commands;

import com.familymoney.telegrambot.business.service.IncomeService;
import com.sessionbot.telegram.commands.ReactiveBotCommand;
import com.sessionbot.telegram.commands.annotations.CommandMethod;
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
        return incomeService.getAllByTelegramUserId(command.getFrom().getId()).collect(Collectors.toList()).map(payments -> {
            String message = payments.stream()
                    .map(payment -> String.format(
                            "%s / %s / %s / %s",
                            payment.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                            payment.getUser().getFullName(),
                            payment.getAccount().getName(),
                            payment.getAmount()))
                    .collect(Collectors.joining("\n", "Поступления: \n", ""));

            return new SendMessage(command.getChatId(), message);
        });
    }
}
