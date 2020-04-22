package com.familymoney.telegrambot.bot.errors.handler;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import reactor.core.publisher.Mono;

public interface ErrorHandler<T extends Throwable> {
    Mono<? extends BotApiMethod<?>> handle(T exception) ;
}
