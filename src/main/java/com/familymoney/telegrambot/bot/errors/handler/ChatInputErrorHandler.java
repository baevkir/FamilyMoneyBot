package com.familymoney.telegrambot.bot.errors.handler;

import com.familymoney.telegrambot.bot.errors.ChatInputValidationException;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ForceReplyKeyboard;
import reactor.core.publisher.Mono;

@Component
public class ChatInputErrorHandler implements ErrorHandler<ChatInputValidationException> {
    @Override
    public Mono<? extends BotApiMethod<?>> handle(Long chatId, ChatInputValidationException exception) {
        return Mono.fromSupplier(() -> {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText(exception.getMessage());

            sendMessage.setReplyMarkup(new ForceReplyKeyboard());
            return sendMessage;
        });
    }
}
