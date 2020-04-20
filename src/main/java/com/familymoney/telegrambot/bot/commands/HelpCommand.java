
package com.familymoney.telegrambot.bot.commands;

import com.familymoney.telegrambot.bot.commands.annotations.CommandMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component
public class HelpCommand extends ReactiveBotCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(HelpCommand.class);

    private List<BotCommand> botCommands;

    @Autowired
    public HelpCommand(List<BotCommand> botCommands) {
        super("help", "Получить список доступных команд.");
        this.botCommands = new ArrayList<>(botCommands);
    }

    @CommandMethod
    public Mono<? extends BotApiMethod<?>> process(Message command) {
        return Mono.fromSupplier(() -> {
            StringBuilder helpMessageBuilder = new StringBuilder("<b>Помощь</b>\n");
            helpMessageBuilder.append("Следующие команды зарегистрированны для бота:\n\n");

            helpMessageBuilder.append(toString()).append("\n\n");

            botCommands.forEach(botCommand -> helpMessageBuilder.append(botCommand.toString()).append("\n\n"));

            SendMessage helpMessage = new SendMessage();
            helpMessage.setChatId(command.getChat().getId());
            helpMessage.enableHtml(true);
            helpMessage.setText(helpMessageBuilder.toString());
            return helpMessage;
        });
    }
}