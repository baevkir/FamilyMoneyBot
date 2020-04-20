package com.familymoney.telegrambot.bot.commands;

import com.familymoney.telegrambot.bot.CommandRequest;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import reactor.core.publisher.Mono;

public interface BotCommand {
    /**
     * Get the identifier of this command
     *
     * @return the identifier
     */
    String getCommandIdentifier();

    /**
     * Get the description of this command
     *
     * @return the description as String
     */
    String getDescription();

    /**
     * Process the message
     */
    Mono<? extends BotApiMethod<?>> process(CommandRequest commandRequest);
}
