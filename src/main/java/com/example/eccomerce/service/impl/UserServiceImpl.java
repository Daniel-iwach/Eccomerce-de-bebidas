package com.example.eccomerce.service.impl;

import com.example.eccomerce.mappers.UserMapper;
import com.example.eccomerce.model.UserEntity;
import com.example.eccomerce.model.dtos.request.RequestUserRegisterDto;
import com.example.eccomerce.model.dtos.response.ResponseUserDto;
import com.example.eccomerce.model.enums.ERole;
import com.example.eccomerce.model.enums.EUserState;
import com.example.eccomerce.repository.UserRepository;
import com.example.eccomerce.service.interfaces.ICartService;
import com.example.eccomerce.service.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;

    private final ICartService cartService;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseUserDto create(RequestUserRegisterDto user) {
        UserEntity userEntity= userMapper.userRegisterToUserEntity(user);

        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setState(EUserState.DESACTIVADO);

        Set<ERole> roleSet=new LinkedHashSet<>();
        roleSet.add(ERole.USER);
        userEntity.setRoleList(roleSet);
        
        userEntity=userRepository.save(userEntity);
        cartService.createCart(userEntity.getId());
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

    @Override
    public ResponseUserDto findByEmail(String email) {
        return userMapper.userEntityToUserDto(userRepository.findByEmail(email)
                .orElseThrow(()->new NoSuchElementException("user with email: "+email+" not found")));
    }
}
