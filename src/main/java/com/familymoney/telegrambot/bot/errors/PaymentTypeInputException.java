package com.familymoney.telegrambot.bot.errors;

public class PaymentTypeInputException extends ChatInputValidationException {
    public PaymentTypeInputException(Long chatId, String message) {
        super(chatId, message);
    }
}
