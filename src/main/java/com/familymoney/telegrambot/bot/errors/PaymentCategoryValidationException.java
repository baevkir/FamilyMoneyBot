package com.familymoney.telegrambot.bot.errors;

import com.sessionbot.telegram.errors.ErrorData;
import com.sessionbot.telegram.errors.exception.validation.ChatValidationException;

public class PaymentCategoryValidationException extends ChatValidationException {
    public PaymentCategoryValidationException(ErrorData errorData) {
        super(errorData);
    }
}
