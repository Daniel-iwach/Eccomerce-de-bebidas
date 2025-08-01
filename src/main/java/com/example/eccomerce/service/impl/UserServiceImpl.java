package com.example.eccomerce.service.impl;

import com.example.eccomerce.mappers.UserMapper;
import com.example.eccomerce.model.UserEntity;
import com.example.eccomerce.model.dtos.request.RequestUserRegisterDto;
import com.example.eccomerce.model.dtos.response.ResponseUserDto;
import com.example.eccomerce.repository.UserRepository;
import com.example.eccomerce.service.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public ResponseUserDto create(RequestUserRegisterDto user) {
        UserEntity userEntity= userMapper.userRegisterToUserEntity(user);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity=userRepository.save(userEntity);
        return userMapper.userEntityToUserDto(userEntity);
    }

    @Override
    public List<ResponseUserDto> listAll() {
        List<UserEntity>userEntityList=userRepository.findAll();
        return userMapper.listEntityToListDto(userEntityList);
    }

    @Override
    public ResponseUserDto findById(String id) {
        return null;
    }
}
