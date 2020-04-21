package com.familymoney.telegrambot.bot.commands;

import com.familymoney.telegrambot.bot.commands.annotations.CommandMethod;
import com.familymoney.telegrambot.bot.commands.annotations.Param;
import com.familymoney.telegrambot.business.model.PaymentType;
import com.familymoney.telegrambot.business.service.payment.PaymentTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import reactor.core.publisher.Mono;

@Component
public class PaymentTypeCommand extends ReactiveBotCommand {

    private PaymentTypeService paymentTypeService;

    @Autowired
    public PaymentTypeCommand(PaymentTypeService paymentTypeService) {
        super("paymentType", "Создать вид оплаты в системе.");
        this.paymentTypeService = paymentTypeService;
    }

    @CommandMethod
    public Mono<? extends BotApiMethod<?>> process(Message command, @Param(index = 0, displayName = "Имя вида оплаты") String type) {
        return paymentTypeService.create(PaymentType.builder()
                .chatId(command.getChatId())
                .name(type)
                .build())
                .map(result -> new SendMessage(command.getChatId(), String.format("Вид оплаты %s успешно создан.", type)));
    }


}
