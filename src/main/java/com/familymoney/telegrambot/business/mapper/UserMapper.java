package com.familymoney.telegrambot.business.mapper;

import com.familymoney.telegrambot.business.model.BotUser;
import com.familymoney.telegrambot.persistence.entity.UserEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
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

    @AfterMapping
    default void updateEntity(@MappingTarget UserEntity entity) {
        if (entity.getUserName() == null) {
            entity.setUserName(entity.getFirstName() + " " + entity.getLastName());
        }
    }

    @AfterMapping
    default void updateUser(@MappingTarget BotUser user) {
        if (user.getUserName() == null) {
            user.setUserName(user.getFirstName() + " " + user.getLastName());
        }
    }
}
