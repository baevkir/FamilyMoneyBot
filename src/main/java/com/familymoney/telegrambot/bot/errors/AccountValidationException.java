package com.familymoney.telegrambot.bot.errors;

import com.sessionbot.telegram.errors.ErrorData;
import com.sessionbot.telegram.errors.exception.validation.ChatValidationException;

public class AccountValidationException extends ChatValidationException {
    public AccountValidationException(ErrorData errorData) {
        super(errorData);
    }
}
