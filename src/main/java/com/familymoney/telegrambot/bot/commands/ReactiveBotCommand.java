package com.familymoney.telegrambot.bot.commands;

import com.familymoney.telegrambot.bot.CommandRequest;
import com.familymoney.telegrambot.bot.cash.CommandsCash;
import com.familymoney.telegrambot.bot.commands.annotations.CommandInvoker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

@Slf4j
public abstract class ReactiveBotCommand implements BotCommand {

    public final static String COMMAND_INIT_CHARACTER = "/";

    private CommandsCash commandsCash;
    private String commandIdentifier;
    private String description;
    private CommandInvoker invoker;

    public ReactiveBotCommand(String commandIdentifier, String description) {
        this.commandIdentifier = commandIdentifier;
        this.description = description;
    }

    @Override
    public Mono<? extends BotApiMethod<?>> process(CommandRequest commandRequest) {
        var invocationResult = invoker.invoke(commandRequest);
        commandsCash.updateArgumentsInChain(
                commandRequest.getCommandMessage().getFrom().getId(),
                commandRequest.getCommandMessage().getChatId(),
                invocationResult.getCommandArguments()
        );
        if (invocationResult.hasErrors()) {
            return Mono.error(invocationResult.getInvocationError());
        }
        return invocationResult.getInvocation();
    }

    @Override
    public String getCommandIdentifier() {
        return commandIdentifier;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Autowired
    public void setCommandsCash(CommandsCash commandsCash) {
        this.commandsCash = commandsCash;
    }

    @PostConstruct
    public void init() {
        log.trace("Start to find OperationMethod in class {}.", getClass());
        invoker = new CommandInvoker(this);
    }

    @Override
    public String toString() {
        return "<b>" + COMMAND_INIT_CHARACTER + getCommandIdentifier() +
                "</b>\n" + getDescription();
    }
}
