package com.familymoney.telegrambot.bot.errors.handler;

import com.familymoney.telegrambot.bot.errors.MethodArgumentValidationException;
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
public class MethodArgumentValidationErrorHandler implements ErrorHandler<MethodArgumentValidationException> {
    @Override
    public Mono<? extends BotApiMethod<?>> handle(MethodArgumentValidationException exception) {
        Long chatId = exception.getErrorData().getCommandRequest().getCommandMessage().getChatId();
        return Mono.just(exception.getErrorData().getOptions())
                .map(options -> {
                    InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
                    List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
                    List<InlineKeyboardButton> rowInline = options.stream()
                            .map(option ->
                                    new InlineKeyboardButton().setText(option).setCallbackData(option))
                            .collect(Collectors.toList());

                    rowsInline.add(rowInline);
                    markupInline.setKeyboard(rowsInline);

                    return new SendMessage().setChatId(chatId).setText(exception.getMessage()).setReplyMarkup(markupInline);
                });
    }
}
