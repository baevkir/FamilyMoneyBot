package com.familymoney.telegrambot.bot.errors.exception.validation;

import com.familymoney.telegrambot.bot.errors.ErrorData;
import lombok.Data;

@Data
public class ChatValidationException extends RuntimeException {

    private ErrorData errorData;

    public ChatValidationException(ErrorData errorData) {
        super(errorData.getText());
        this.errorData = errorData;
    }
}
