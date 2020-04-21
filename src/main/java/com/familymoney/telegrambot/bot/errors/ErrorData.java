package com.familymoney.telegrambot.bot.errors;

import com.familymoney.telegrambot.bot.CommandRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Builder(builderClassName = "Builder")
public class ErrorData {
    private CommandRequest commandRequest;
    private String text;
    private Set<String> options;
}
