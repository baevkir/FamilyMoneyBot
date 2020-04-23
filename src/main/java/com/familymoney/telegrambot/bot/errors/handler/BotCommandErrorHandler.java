package com.familymoney.telegrambot.bot.errors.handler;

import com.familymoney.telegrambot.bot.cash.CommandsSessionCash;
import com.familymoney.telegrambot.bot.errors.exception.BotCommandException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class BotCommandErrorHandler implements ErrorHandler<BotCommandException>{
    private CommandsSessionCash commandsSessionCash;

    public BotCommandErrorHandler(CommandsSessionCash commandsSessionCash) {
        this.commandsSessionCash = commandsSessionCash;
    }

    @Override
    public Mono<? extends BotApiMethod<?>> handle(BotCommandException exception) {
        String botMessage = "Error during chat bot command. Please try again letter.";
        Long chatId = exception.getCommandRequest().getCommandMessage().getChatId();
        log.error(botMessage, exception);
        return Mono.fromSupplier(() -> {
            commandsSessionCash.closeSession(exception.getCommandRequest().getCommandMessage());

            return new SendMessage()
                    .setChatId(chatId)
                    .setText(botMessage);
        });
    }
}
