package com.familymoney.bot.mapper;

import com.familymoney.model.BotUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.telegram.telegrambots.meta.api.objects.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(source = "id", target = "telegramId"),
            @Mapping(target = "id", ignore = true),
    })
    BotUser fromTelegramPojo(User user);
}
