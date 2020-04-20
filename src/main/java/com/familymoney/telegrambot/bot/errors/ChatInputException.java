package com.familymoney.telegrambot.bot.errors;

public class ChatInputException extends RuntimeException {

    private Long chatId;

    public ChatInputException(Long chatId, String message) {
        super(message);
        this.chatId = chatId;
    }

    public Long getChatId() {
        return chatId;
    }
}
