package com.familymoney.telegrambot.bot.errors;

public class PaymentTypeInputException extends ChatInputException {
    public PaymentTypeInputException(Long chatId, String message) {
        super(chatId, message);
    }
}
