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
public class CommandsCash {
    private Map<CommandsCashKey, CommandsCashValue> cash = new ConcurrentHashMap<>();

    public CommandsCashValue openNewChain(Message commandMessage, String command, List<Object> arguments) {
        Objects.requireNonNull(commandMessage, "command is null");
        Assert.isTrue(commandMessage.isCommand(), "message is not a command");

        CommandsCashValue cashValue = new CommandsCashValue(commandMessage, command, arguments);
        cash.put(new CommandsCashKey(commandMessage), cashValue);
        return cashValue;
    }

    public CommandsCashValue getChain(Integer userId, Long chatId) {
        Objects.requireNonNull(userId, "userId is null");
        Objects.requireNonNull(chatId, "chatId is null");
        return cash.get(new CommandsCashKey(userId, chatId));
    }

    public CommandsCashValue updateArgumentsInChain(Integer userId, Long chatId, List<Object> arguments) {
        Objects.requireNonNull(userId, "userId is null");
        Objects.requireNonNull(chatId, "chatId is null");
        Objects.requireNonNull(arguments, "arguments is null");
        CommandsCashValue cashValue = cash.get(new CommandsCashKey(userId, chatId));
        cashValue.arguments = arguments;
        return cashValue;
    }

    public void closeChain(Message commandMessage) {
        Objects.requireNonNull(commandMessage, "command is null");
        Assert.isTrue(commandMessage.isCommand(), "message is not a command");

        cash.remove(new CommandsCashKey(commandMessage));
    }

    private static class CommandsCashKey {
        private Integer userId;
        private Long chatId;

        public CommandsCashKey(Message message) {
            this.userId = message.getFrom().getId();
            this.chatId = message.getChatId();
        }

        public CommandsCashKey(Integer userId, Long chatId) {
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
            CommandsCashKey that = (CommandsCashKey) object;
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
    public static class CommandsCashValue {
        private Message commandMessage;
        private String command;
        private List<Object> arguments;

    }
}
