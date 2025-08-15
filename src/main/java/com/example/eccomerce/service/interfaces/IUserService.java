package com.example.eccomerce.service.interfaces;

import com.example.eccomerce.model.dtos.request.RequestUserRegisterDto;
import com.example.eccomerce.model.dtos.response.ResponseUserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IUserService {

    ResponseUserDto create(RequestUserRegisterDto user);

    List<ResponseUserDto> listAll();

    ResponseUserDto findById(String id);

    ResponseUserDto findByEmail(String email);
}
