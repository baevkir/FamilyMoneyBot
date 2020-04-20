package com.familymoney.telegrambot.bot.commands;

import com.familymoney.telegrambot.bot.commands.annotations.CommandMethod;
import com.familymoney.telegrambot.business.service.PaymentService;
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
        super("payments", "Получить полный список платежей чата.");
        this.paymentService = paymentService;
    }

    @CommandMethod
    public Mono<? extends BotApiMethod<?>> process(Message command) {
        return paymentService.getAllPayments(command.getChatId()).collect(Collectors.toList()).map(payments -> {
            String message = payments.stream()
                    .map(payment -> String.format(
                            "Дата: %s Вид Оплаты: %s Пользователь: %s Сумма: %s",
                            payment.getPaymentDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                            payment.getType(),
                            payment.getUser().getUserName(),
                            payment.getAmount()))
                    .collect(Collectors.joining("\n", "Платежи:\n", ""));

            return new SendMessage(command.getChatId(), message);
        });
    }
}
