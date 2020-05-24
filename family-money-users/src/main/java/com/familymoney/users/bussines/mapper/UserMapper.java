package com.familymoney.users.bussines.mapper;

import com.familymoney.model.BotUser;
import com.familymoney.users.persistence.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    BotUser fromEntity(UserEntity userEntity);

    UserEntity toEntity(BotUser user);
}
