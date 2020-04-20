package com.familymoney.telegrambot.bot;

import com.familymoney.telegrambot.bot.commands.BotCommand;
import com.familymoney.telegrambot.bot.commands.HelpCommand;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CommandsFactory {
    private HelpCommand helpCommand;
    private List<BotCommand> botCommands;

    private final Map<String, BotCommand> commandRegistryMap = new HashMap<>();

    public CommandsFactory(HelpCommand helpCommand, List<BotCommand> botCommands) {
        this.helpCommand = helpCommand;
        this.botCommands = botCommands;
    }

    public final BotCommand getHelpCommand() {
        return helpCommand;
    }

    public final BotCommand getCommand(String commandName) {
        return commandRegistryMap.getOrDefault(commandName, helpCommand);
    }

    @PostConstruct
    public void start() {
        botCommands.forEach(command -> commandRegistryMap.put(command.getCommandIdentifier(), command));
    }
}
