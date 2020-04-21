package com.familymoney.telegrambot.bot.errors;

public class ChatInputValidationException extends RuntimeException {

    private Long chatId;

    public ChatInputValidationException(Long chatId, String message) {
        super(message);
        this.chatId = chatId;
    }

    public Long getChatId() {
        return chatId;
    }
}
