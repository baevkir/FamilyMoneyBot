package com.familymoney.telegrambot.bot.commands.annotations;

import com.familymoney.telegrambot.bot.errors.ChatInputValidationException;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Param {
    int index();
    String displayName();
    Class<? extends ChatInputValidationException> errorType() default ChatInputValidationException.class;
}
