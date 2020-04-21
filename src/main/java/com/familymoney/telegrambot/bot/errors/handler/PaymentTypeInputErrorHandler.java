package com.familymoney.telegrambot.bot.errors.handler;

import com.familymoney.telegrambot.bot.errors.PaymentTypeInputException;
import com.familymoney.telegrambot.business.service.AccountService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PaymentTypeInputErrorHandler implements ErrorHandler<PaymentTypeInputException> {
    private AccountService paymentTypeService;

    public PaymentTypeInputErrorHandler(AccountService paymentTypeService) {
        this.paymentTypeService = paymentTypeService;
    }

    @Override
    public Mono<? extends BotApiMethod<?>> handle(Long chatId, PaymentTypeInputException exception) {
        return paymentTypeService.getAll(chatId).collect(Collectors.toList())
            .map(paymentTypes -> {
                InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
                List<InlineKeyboardButton> rowInline = paymentTypes.stream()
                        .map(paymentType ->
                                new InlineKeyboardButton().setText(paymentType.getName()).setCallbackData(paymentType.getName()))
                        .collect(Collectors.toList());

                rowsInline.add(rowInline);
                markupInline.setKeyboard(rowsInline);

                return new SendMessage().setChatId(chatId).setText(exception.getMessage()).setReplyMarkup(markupInline);
            });
    }
}
