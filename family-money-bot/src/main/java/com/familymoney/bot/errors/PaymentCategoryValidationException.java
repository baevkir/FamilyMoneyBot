package com.familymoney.bot.errors;

import com.sessionbot.errors.ErrorData;
import com.sessionbot.errors.exception.validation.ChatValidationException;

public class PaymentCategoryValidationException extends ChatValidationException {
    public PaymentCategoryValidationException(ErrorData errorData) {
        super(errorData);
    }
}
