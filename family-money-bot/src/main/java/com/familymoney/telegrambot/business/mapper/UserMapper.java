package com.familymoney.telegrambot.business.mapper;

import com.familymoney.model.BotUser;
import com.familymoney.telegrambot.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.telegram.telegrambots.meta.api.objects.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    BotUser fromEntity(UserEntity userEntity);

    @Mappings({
            @Mapping(source = "id", target = "telegramId"),
            @Mapping(target = "id", ignore = true),
    })
    BotUser fromTelegramPojo(User user);

    UserEntity toEntity(BotUser user);
}
