package com.familymoney.telegrambot.bot.commands;

import com.familymoney.telegrambot.bot.CommandRequest;
import com.familymoney.telegrambot.bot.cash.CommandsCash;
import com.familymoney.telegrambot.bot.commands.annotations.CommandMethod;
import com.familymoney.telegrambot.bot.commands.annotations.Param;
import com.familymoney.telegrambot.bot.errors.ChatInputValidationException;
import com.familymoney.telegrambot.bot.errors.handler.ChatInputErrorHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ReflectionUtils;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.stream.Collectors;

public abstract class ReactiveBotCommand implements BotCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReactiveBotCommand.class);

    public final static String COMMAND_INIT_CHARACTER = "/";

    private CommandsCash commandsCash;
    private String commandIdentifier;
    private String description;
    private Method invokerMethod;
    private ObjectMapper mapper = new ObjectMapper();

    public ReactiveBotCommand(String commandIdentifier, String description) {
        this.commandIdentifier = commandIdentifier;
        this.description = description;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Mono<? extends BotApiMethod<?>> process(CommandRequest commandRequest) {
        return Flux.fromIterable(Arrays.asList(invokerMethod.getParameters()))
                .map(parameter -> getMethodArgument(parameter, commandRequest))
                .collect(Collectors.toList())
                .flatMap(methodArguments -> {
                    ReflectionUtils.makeAccessible(invokerMethod);
                    return (Mono) ReflectionUtils.invokeMethod(invokerMethod, this, methodArguments.toArray());
                });
    }

    @Override
    public String getCommandIdentifier() {
        return commandIdentifier;
    }

    @Override
    public String getDescription() {
        return description;
    }

    private Object getMethodArgument(Parameter parameter, CommandRequest commandRequest) {
        if (Message.class.equals(parameter.getType())) {
            if (parameter.getName().equals("command")) {
                return commandRequest.getCommandMessage();
            } else if (parameter.getName().equals("update")) {
                return commandRequest.getUpdate();
            }
        }
        Param param = parameter.getAnnotation(Param.class);
        int index = param.index();
        if (index < commandRequest.getArguments().size()) {
            return mapper.convertValue(commandRequest.getArguments().get(index), parameter.getType());
        }
        if (index == commandRequest.getArguments().size() && commandRequest.getPendingArgument() != null) {
            Object convertValue = mapper.convertValue(commandRequest.getPendingArgument(), parameter.getType());
            commandsCash.addArgumentToChain(
                    commandRequest.getCommandMessage().getFrom().getId(),
                    commandRequest.getCommandMessage().getChatId(),
                    commandRequest.getPendingArgument()
            );
            return convertValue;
        }
        throw createException(commandRequest, param);
    }

    private ChatInputValidationException createException(CommandRequest commandRequest, Param param) {
        String message = String.format("Пожалуйста укажите %s.", param.displayName());
        try {
            Constructor<? extends ChatInputValidationException> constructor = param.errorType().getConstructor(Long.class, String.class);
            return constructor.newInstance(commandRequest.getCommandMessage().getChatId(), message);
        } catch (Exception exception) {
            throw new RuntimeException();
        }
    }

    @Autowired
    public void setCommandsCash(CommandsCash commandsCash) {
        this.commandsCash = commandsCash;
    }

    @PostConstruct
    public void init() {
        LOGGER.trace("Start to find OperationMethod in class {}.", getClass());
        invokerMethod = Arrays.stream(getClass().getMethods())
                .filter(method -> method.isAnnotationPresent(CommandMethod.class))
                .peek(method -> LOGGER.debug("Find OperationMethod {} for class {}.", method, getClass()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(String.format("Can't execute %s. There is no class annotated @CommandMethod.", getClass())));
    }

    @Override
    public String toString() {
        return "<b>" + COMMAND_INIT_CHARACTER + getCommandIdentifier() +
                "</b>\n" + getDescription();
    }
}
