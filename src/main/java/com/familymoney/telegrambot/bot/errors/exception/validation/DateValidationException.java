package com.familymoney.telegrambot.bot.errors.exception.validation;

import com.familymoney.telegrambot.bot.errors.ErrorData;

public class DateValidationException extends ChatValidationException {
    public DateValidationException(ErrorData errorData) {
        super(errorData);
    }
}
