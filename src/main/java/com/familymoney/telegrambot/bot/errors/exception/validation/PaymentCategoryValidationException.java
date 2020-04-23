package com.familymoney.telegrambot.bot.errors.exception.validation;

import com.familymoney.telegrambot.bot.errors.ErrorData;

public class PaymentCategoryValidationException extends ChatValidationException {
    public PaymentCategoryValidationException(ErrorData errorData) {
        super(errorData);
    }
}
