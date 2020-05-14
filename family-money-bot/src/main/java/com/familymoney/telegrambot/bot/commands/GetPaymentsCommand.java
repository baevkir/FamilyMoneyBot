package com.familymoney.telegrambot.bot.commands;

import com.familymoney.telegrambot.business.service.payment.PaymentService;
import com.sessionbot.commands.ReactiveBotCommand;
import com.sessionbot.commands.annotations.CommandMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import reactor.core.publisher.Mono;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Component
public class GetPaymentsCommand extends ReactiveBotCommand {
    private PaymentService paymentService;

    @Autowired
    public GetPaymentsCommand(PaymentService paymentService) {
        super("payments", "Получить полный список расходов.");
        this.paymentService = paymentService;
    }

    @CommandMethod
    public Mono<? extends BotApiMethod<?>> process(Message command) {
        return paymentService.getAllByTelegramUserId(command.getFrom().getId()).collect(Collectors.toList()).map(payments -> {
            String message = payments.stream()
                    .map(payment -> String.format(
                            "%s / %s / %s / %s / %s",
                            payment.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                            payment.getUser().getFullName(),
                            payment.getAccount().getName(),
                            payment.getCategory().getName(),
                            payment.getAmount()))
                    .collect(Collectors.joining("\n", "Платежи:\n", ""));

            return new SendMessage(command.getChatId(), message);
        });
    }
}
