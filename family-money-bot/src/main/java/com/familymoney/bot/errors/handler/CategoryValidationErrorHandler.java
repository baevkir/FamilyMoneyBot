package com.familymoney.bot.errors.handler;

import com.familymoney.bot.errors.PaymentCategoryValidationException;
import com.familymoney.bot.service.PaymentCategoryService;
import com.sessionbot.errors.handler.ErrorHandler;
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
public class CategoryValidationErrorHandler implements ErrorHandler<PaymentCategoryValidationException> {
    private PaymentCategoryService paymentCategoryService;

    public CategoryValidationErrorHandler(PaymentCategoryService paymentCategoryService) {
        this.paymentCategoryService = paymentCategoryService;
    }

    @Override
    public Mono<? extends BotApiMethod<?>> handle(PaymentCategoryValidationException exception) {
        Long chatId = exception.getErrorData().getCommandRequest().getCommandMessage().getChatId();
        Integer telegramId = exception.getErrorData().getCommandRequest().getCommandMessage().getFrom().getId();
        return paymentCategoryService.getAllForTelegramUserId(telegramId).collect(Collectors.toList())
            .map(paymentTypes -> {
                InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
                List<InlineKeyboardButton> rowInline = paymentTypes.stream()
                        .map(paymentCategory ->
                                new InlineKeyboardButton().setText(paymentCategory.getName()).setCallbackData(paymentCategory.getName()))
                        .collect(Collectors.toList());

                rowsInline.add(rowInline);
                markupInline.setKeyboard(rowsInline);

                return new SendMessage().setChatId(chatId).setText(exception.getMessage()).setReplyMarkup(markupInline);
            });
    }
}
