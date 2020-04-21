package com.familymoney.telegrambot.bot.errors;

import lombok.RequiredArgsConstructor;

public class AccountValidationException extends ChatValidationException {
    public AccountValidationException(ErrorData errorData) {
        super(errorData);
    }
}
