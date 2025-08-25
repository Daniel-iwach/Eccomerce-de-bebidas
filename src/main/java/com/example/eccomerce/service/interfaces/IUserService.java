package com.example.eccomerce.service.interfaces;

import com.example.eccomerce.model.UserEntity;
import com.example.eccomerce.model.dtos.request.RequestUserRegisterDto;
import com.example.eccomerce.model.dtos.response.ResponseUserDto;
import com.example.eccomerce.model.enums.EUserState;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public interface IUserService {

    ResponseUserDto create(RequestUserRegisterDto user);

    List<ResponseUserDto> listAll();

    List<ResponseUserDto> findUsersActivated();

    Long countUsersRegisteredThisMonth();

    ResponseUserDto findById(String id);

    ResponseUserDto findByEmail(String email);

    String changeStateById(@PathVariable String userId);
}
