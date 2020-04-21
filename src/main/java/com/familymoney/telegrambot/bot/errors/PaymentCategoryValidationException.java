package com.familymoney.telegrambot.bot.errors;

import lombok.RequiredArgsConstructor;

public class PaymentCategoryValidationException extends ChatValidationException {
    public PaymentCategoryValidationException(ErrorData errorData) {
        super(errorData);
    }
}
