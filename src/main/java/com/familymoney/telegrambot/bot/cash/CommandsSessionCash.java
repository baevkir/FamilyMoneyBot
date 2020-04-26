package com.familymoney.telegrambot.bot.cash;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CommandsSessionCash {
    private Map<SessionKey, SessionValue> cash = new ConcurrentHashMap<>();

    public SessionValue openNewSession(Message commandMessage, String command, List<Object> arguments) {
        Objects.requireNonNull(commandMessage, "command is null");
        Assert.isTrue(commandMessage.isCommand(), "message is not a command");

        SessionValue cashValue = new SessionValue(commandMessage, command, arguments);
        cash.put(new SessionKey(commandMessage), cashValue);
        return cashValue;
    }

    public SessionValue getSession(Integer userId, Long chatId) {
        Objects.requireNonNull(userId, "userId is null");
        Objects.requireNonNull(chatId, "chatId is null");
        return cash.get(new SessionKey(userId, chatId));
    }

    public SessionValue updateSessionArguments(Integer userId, Long chatId, List<Object> arguments) {
        Objects.requireNonNull(userId, "userId is null");
        Objects.requireNonNull(chatId, "chatId is null");
        Objects.requireNonNull(arguments, "arguments is null");
        SessionValue cashValue = cash.get(new SessionKey(userId, chatId));
        if (cashValue != null) {
            cashValue.arguments = arguments;
        }
        return cashValue;
    }

    public void closeSession(Message commandMessage) {
        Objects.requireNonNull(commandMessage, "command is null");
        Assert.isTrue(commandMessage.isCommand(), "message is not a command");

        cash.remove(new SessionKey(commandMessage));
    }

    private static class SessionKey {
        private Integer userId;
        private Long chatId;

        public SessionKey(Message message) {
            this.userId = message.getFrom().getId();
            this.chatId = message.getChatId();
        }

        public SessionKey(Integer userId, Long chatId) {
            this.userId = userId;
            this.chatId = chatId;
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object == null || getClass() != object.getClass()) {
                return false;
            }
            SessionKey that = (SessionKey) object;
            return Objects.equals(userId, that.userId) &&
                    Objects.equals(chatId, that.chatId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(userId, chatId);
        }
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class SessionValue {
        private Message commandMessage;
        private String command;
        private List<Object> arguments;

    }
}
