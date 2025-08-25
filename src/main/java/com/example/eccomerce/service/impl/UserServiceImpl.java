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
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final MongoTemplate mongoTemplate;

    private final UserRepository userRepository;

    private final ICartService cartService;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseUserDto create(RequestUserRegisterDto user) {
        UserEntity userEntity= userMapper.userRegisterToUserEntity(user);

        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setState(EUserState.DESACTIVADO);
        userEntity.setRegisterDate(new Date());

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
    public List<ResponseUserDto> findUsersActivated() {
        return userMapper.listEntityToListDto(userRepository.findByState(EUserState.ACTIVADO));
    }

    @Override
    public Long countUsersRegisteredThisMonth() {
        ZoneId zone = ZoneId.of("America/Argentina/Buenos_Aires");

        LocalDate firstDay = LocalDate.now(zone).withDayOfMonth(1);
        LocalDate firstDayNextMonth = firstDay.plusMonths(1);

        Date start = Date.from(firstDay.atStartOfDay(zone).toInstant());
        Date end   = Date.from(firstDayNextMonth.atStartOfDay(zone).toInstant());

        Query query = new Query();
        query.addCriteria(Criteria.where("state").is(EUserState.ACTIVADO)
                .and("registerDate").gte(start).lt(end));

        long total = mongoTemplate.count(query, UserEntity.class);
        Long totalUsers = Long.valueOf(total);
        return totalUsers;
    }


    @Override
    public ResponseUserDto findById(String id) {
        return userMapper.userEntityToUserDto(userRepository.findById(id)
                .orElseThrow(()->new NoSuchElementException("user with id: "+id+" not found")));
    }

    @Override
    public ResponseUserDto findByEmail(String email) {
        return userMapper.userEntityToUserDto(userRepository.findByEmail(email)
                .orElseThrow(()->new NoSuchElementException("user with email: "+email+" not found")));
    }

    @Override
    public String changeStateById(String userId) {
        UserEntity user=userRepository.findById(userId)
                .orElseThrow(()->new NoSuchElementException("user with id: "+userId+" not found"));
        if (user.getState().equals(EUserState.ACTIVADO)){
            user.setState(EUserState.DESACTIVADO);
        }else {
            user.setState(EUserState.ACTIVADO);
        }
        userRepository.save(user);
        return "Estado actualizado";

    }
}
