package com.example.eccomerce.mappers;

import com.example.eccomerce.model.UserEntity;
import com.example.eccomerce.model.dtos.request.RequestUserRegisterDto;
import com.example.eccomerce.model.dtos.response.ResponseUserDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntity userRegisterToUserEntity(RequestUserRegisterDto userRegisterDto);
    ResponseUserDto userEntityToUserDto(UserEntity userEntity);
    UserEntity userDtoToUserEntity(ResponseUserDto userDto);

    //list
    List<ResponseUserDto>listEntityToListDto(List<UserEntity>userEntityList);
}
