package com.familymoney.telegrambot.bot.errors;

public class PaymentCategoryInputException extends ChatInputValidationException {
    public PaymentCategoryInputException(Long chatId, String message) {
        super(chatId, message);
    }
}
