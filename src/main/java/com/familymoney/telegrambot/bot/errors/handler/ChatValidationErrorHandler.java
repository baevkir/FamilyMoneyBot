package com.familymoney.telegrambot.bot.errors.handler;

import com.familymoney.telegrambot.bot.errors.ChatValidationException;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ForceReplyKeyboard;
import reactor.core.publisher.Mono;

@Component
public class ChatValidationErrorHandler implements ErrorHandler<ChatValidationException> {
    @Override
    public Mono<? extends BotApiMethod<?>> handle(Long chatId, ChatValidationException exception) {
        return Mono.fromSupplier(() -> {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText(exception.getMessage());

            return sendMessage;
        });
    }
}
