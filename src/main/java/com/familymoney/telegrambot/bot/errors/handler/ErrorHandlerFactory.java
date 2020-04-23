package com.familymoney.telegrambot.bot.errors.handler;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Optional;

import static org.apache.commons.lang3.exception.ExceptionUtils.getThrowableList;

@Slf4j
@Component
public class ErrorHandlerFactory {
    private List<ErrorHandler> errorHandlers;
    private Map<Class<Throwable>, ErrorHandler<Throwable>> errorHandlerMap = new HashMap<>();

    public ErrorHandlerFactory(List<ErrorHandler> errorHandlers) {
        this.errorHandlers = errorHandlers;
    }

    @SuppressWarnings("unchecked")
    public Mono<? extends BotApiMethod<?>> handle(Throwable exception) {
        for (Throwable currentError : Lists.reverse(getThrowableList(exception))) {
            ErrorHandler<Throwable> errorHandler = errorHandlerMap.get(currentError.getClass());
            if (errorHandler != null) {
                return errorHandler.handle(currentError);
            }
        }
        log.error("Error during chat bot command", exception);
        return Mono.empty();
    }

    @PostConstruct
    public void init() {
        errorHandlers.forEach(errorHandler -> {
            Class type = ((Class) ((ParameterizedType) errorHandler.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0]);
            errorHandlerMap.put(type, errorHandler);
        });
    }
}
