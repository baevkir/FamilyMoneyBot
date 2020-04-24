package com.familymoney.telegrambot.bot.errors.handler;

import com.familymoney.telegrambot.bot.errors.exception.validation.DateValidationException;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class DateValidationErrorHandler implements ErrorHandler<DateValidationException> {
    private static DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ISO_DATE;
    @Override
    public Mono<? extends BotApiMethod<?>> handle(DateValidationException exception) {
        Long chatId = exception.getErrorData().getCommandRequest().getCommandMessage().getChatId();
        return Mono.fromSupplier(() -> {

            InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
            List<InlineKeyboardButton> rowInline = new ArrayList<>();
            LocalDate currentDate = LocalDate.now();

            rowInline.add(new InlineKeyboardButton()
                    .setText("Сегодня")
                    .setCallbackData(currentDate.format(DATE_FORMAT)));

            rowInline.add(new InlineKeyboardButton()
                    .setText("Вчера")
                    .setCallbackData(currentDate.minusDays(1).format(DATE_FORMAT)));

            rowInline.add(new InlineKeyboardButton()
                    .setText("2 дня назад")
                    .setCallbackData(currentDate.minusDays(2).format(DATE_FORMAT)));

            rowInline.add(new InlineKeyboardButton()
                    .setText("3 дня назад")
                    .setCallbackData(currentDate.minusDays(3).format(DATE_FORMAT)));

            rowsInline.add(rowInline);
            markupInline.setKeyboard(rowsInline);

            return new SendMessage().setChatId(chatId)
                    .setText(exception.getMessage() + String.format(" (Формат: %s)", currentDate.format(DATE_FORMAT)))
                    .setReplyMarkup(markupInline);
        });
    }
}
