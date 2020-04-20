package com.familymoney.telegrambot.bot.errors.handler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ErrorHandlerFactory {
    private ChatInputErrorHandler chatInputErrorHandler;
    private List<ErrorHandler> errorHandlers;
    private Map<Class, ErrorHandler> errorHandlerMap = new HashMap<>();

    public ErrorHandlerFactory(List<ErrorHandler> errorHandlers) {
        this.errorHandlers = errorHandlers;
    }

    @SuppressWarnings("unchecked")
    public Mono<? extends BotApiMethod<?>> handle(Long chatId, Throwable exception) {
        return errorHandlerMap.getOrDefault(exception.getClass(), chatInputErrorHandler).handle(chatId, exception);
    }

    @PostConstruct
    public void init() {
        errorHandlers.forEach(errorHandler -> {
            Class type = ((Class)((ParameterizedType)errorHandler.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0]);
            errorHandlerMap.put(type, errorHandler);
        });
    }
}
