package com.familymoney.bot.errors;

import com.sessionbot.errors.ErrorData;
import com.sessionbot.errors.exception.validation.ChatValidationException;

public class AccountValidationException extends ChatValidationException {
    public AccountValidationException(ErrorData errorData) {
        super(errorData);
    }
}
