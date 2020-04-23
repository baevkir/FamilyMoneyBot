package com.familymoney.telegrambot.bot.errors.exception;

import com.familymoney.telegrambot.bot.CommandRequest;
import lombok.Getter;

@Getter
public class BotCommandException extends RuntimeException{
    private CommandRequest commandRequest;

    public BotCommandException(CommandRequest commandRequest, Throwable cause) {
        super(cause);
        this.commandRequest = commandRequest;
    }
}
