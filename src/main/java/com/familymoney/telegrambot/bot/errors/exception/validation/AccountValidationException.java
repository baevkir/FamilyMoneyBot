package com.familymoney.telegrambot.bot.errors.exception.validation;

import com.familymoney.telegrambot.bot.errors.ErrorData;

public class AccountValidationException extends ChatValidationException {
    public AccountValidationException(ErrorData errorData) {
        super(errorData);
    }
}
