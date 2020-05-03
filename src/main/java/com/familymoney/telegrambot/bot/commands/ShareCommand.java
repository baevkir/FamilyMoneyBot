package com.familymoney.telegrambot.bot.commands;

import com.familymoney.telegrambot.bot.errors.AccountValidationException;
import com.familymoney.telegrambot.business.service.AccountService;
import com.familymoney.telegrambot.business.service.payment.PaymentCategoryService;
import com.sessionbot.commands.ReactiveBotCommand;
import com.sessionbot.commands.annotations.CommandMethod;
import com.sessionbot.commands.annotations.Param;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import reactor.core.publisher.Mono;

@Component
public class ShareCommand extends ReactiveBotCommand {

    private PaymentCategoryService paymentCategoryService;
    private AccountService accountService;

    public ShareCommand(PaymentCategoryService paymentCategoryService, AccountService accountService) {
        super("share", "Распрастранить объекты другому пользователю.");
        this.paymentCategoryService = paymentCategoryService;
        this.accountService = accountService;
    }

    @CommandMethod(argument = "Вид оплаты")
    public Mono<? extends BotApiMethod<?>> shareAccount(
            Message command,
            @Param(index = 1, displayName = "Вид оплаты", errorType = AccountValidationException.class) String type,
            @Param(index = 2, displayName = "Имя пользователя") String userName) {

        return accountService.shareForUser(
                command.getFrom().getId(),
                type,
                userName
        ).thenReturn(
                new SendMessage()
                        .setChatId(command.getChatId())
                        .setText(String.format(" Вид оплаты %s успешно распространен пользователю %s", type, userName))
        );
    }

    @CommandMethod(argument = "Категории")
    public Mono<? extends BotApiMethod<?>> shareCategories(
            Message command,
            @Param(index = 1, displayName = "Имя пользователя") String userName) {

        return paymentCategoryService.shareForUser(
                command.getFrom().getId(),
                userName
        ).thenReturn(new SendMessage().setChatId(command.getChatId()).setText("Категории успешно распространены пользователю " + userName));
    }
}
