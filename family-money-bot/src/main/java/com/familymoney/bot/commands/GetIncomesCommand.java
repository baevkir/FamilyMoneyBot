package com.familymoney.bot.commands;

import com.familymoney.bot.service.IncomeService;
import com.sessionbot.commands.ReactiveBotCommand;
import com.sessionbot.commands.annotations.CommandMethod;
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
        return incomeService.getAllByTelegramUserId(command.getFrom().getId()).collect(Collectors.toList()).map(incomes -> {
            String message = incomes.stream()
                    .map(income -> String.format(
                            "%s / %s / %s / %s",
                            income.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                            income.getUser().getFullName(),
                            income.getAccount().getName(),
                            income.getAmount()))
                    .collect(Collectors.joining("\n", "Поступления: \n", ""));

            return new SendMessage(command.getChatId(), message);
        });
    }
}
