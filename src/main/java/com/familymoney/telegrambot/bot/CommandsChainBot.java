package com.familymoney.telegrambot.bot;

import com.familymoney.telegrambot.bot.cash.CommandsCash;
import com.familymoney.telegrambot.bot.errors.handler.ErrorHandlerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class CommandsChainBot extends TelegramLongPollingBot {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommandsChainBot.class);

    @Value("${bot.token}")
    private String token;

    @Value("${bot.username}")
    private String username;

    private CommandsFactory commandsFactory;
    private CommandsCash commandsCash;
    private ErrorHandlerFactory errorHandler;

    public CommandsChainBot(CommandsFactory commandsFactory, CommandsCash commandsCash, ErrorHandlerFactory errorHandler) {
        this.commandsFactory = commandsFactory;
        this.commandsCash = commandsCash;
        this.errorHandler = errorHandler;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Optional<CommandRequest> commandRequest = getCommandRequest(update);

        if (commandRequest.isEmpty()) {
            executeHelp(update);
            return;
        }

        commandsFactory.getCommand(commandRequest.get().getCommand())
                .process(commandRequest.get())
                .subscribe(answer -> {
                            commandsCash.closeChain(commandRequest.get().getCommandMessage());
                            executeMessage(answer);
                        },
                        error -> errorHandler.handle(commandRequest.get().getCommandMessage().getChatId(), error).subscribe(this::executeMessage));
    }

    @Override
    public String getBotToken() {
        return token;
    }


    @Override
    public String getBotUsername() {
        return username;
    }

    private Optional<CommandRequest> getCommandRequest(Update update) {
        if (update.hasMessage() && update.getMessage().isCommand()) {
            return Optional.of(registerNewCommandInCash(update.getMessage(), update));
        }
        if (update.hasMessage()) {
            return Optional.ofNullable(commandsCash.getChain(update.getMessage().getFrom().getId(), update.getMessage().getChatId()))
                    .map(cashValue -> toRequest(cashValue)
                            .withUpdate(update)
                            .withPendingArgument(update.getMessage().getText())
                            .build());


        }
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            Message message = callbackQuery.getMessage();
            return Optional.ofNullable(commandsCash.getChain(callbackQuery.getFrom().getId(), message.getChatId()))
                    .map(cashValue -> toRequest(cashValue)
                            .withUpdate(update)
                            .withPendingArgument(callbackQuery.getData())
                            .build());
        }
        return Optional.empty();
    }

    private CommandRequest registerNewCommandInCash(Message commandMessage, Update update) {
        Assert.isTrue(commandMessage.isCommand(), "Command should be command.");
        Assert.isTrue(commandMessage.hasText(), "Command should contains text.");

        String commandText = commandMessage.getText().substring(1);
        String[] commandSplit = commandText.split(BotCommand.COMMAND_PARAMETER_SEPARATOR_REGEXP);

        String command = commandSplit[0];
        List<Object> arguments = Stream.of(commandSplit).skip(1).collect(Collectors.toList());

        CommandsCash.CommandsCashValue cashValue = commandsCash.openNewChain(commandMessage, command, arguments);

        return toRequest(cashValue)
                .withUpdate(update)
                .build();
    }

    private CommandRequest.Builder toRequest(CommandsCash.CommandsCashValue cashValue) {
        return CommandRequest.builder()
                .withCommandMessage(cashValue.getCommandMessage())
                .withCommand(cashValue.getCommand())
                .withArguments(new ArrayList<>(cashValue.getArguments()));
    }

    private void executeHelp(Update update) {
        CommandRequest commandRequest = CommandRequest.builder()
                .withCommandMessage(update.getMessage())
                .withUpdate(update).build();
        commandsFactory.getHelpCommand().process(commandRequest).subscribe(this::executeMessage);
    }

    private void executeMessage(BotApiMethod<?> message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            LOGGER.error("Cannot execute message");
        }
    }
}
