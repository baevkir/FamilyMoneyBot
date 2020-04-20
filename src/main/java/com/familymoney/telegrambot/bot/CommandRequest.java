package com.familymoney.telegrambot.bot;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Getter
@Builder(setterPrefix = "with", builderClassName = "Builder")
public class CommandRequest {
    private Message commandMessage;
    private Update update;
    private String command;
    private List<Object> arguments;
    private Object pendingArgument;

}
