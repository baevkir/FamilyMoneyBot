package com.familymoney.telegrambot.bot.errors;

public class MethodArgumentValidationException extends ChatValidationException {
    public MethodArgumentValidationException(ErrorData errorData) {
        super(errorData);
    }
}
