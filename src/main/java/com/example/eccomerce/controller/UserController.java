package com.example.eccomerce.controller;

import com.example.eccomerce.model.dtos.request.RequestUserRegisterDto;
import com.example.eccomerce.model.dtos.response.ResponseUserDto;
import com.example.eccomerce.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<ResponseUserDto>> getAll() {
        return new ResponseEntity<>(userService.listAll(), HttpStatus.OK);
    }

    @GetMapping("/getById/{userId}")
    public ResponseEntity<ResponseUserDto> getUserById(@PathVariable String userId) {
        return new ResponseEntity<>(userService.findById(userId), HttpStatus.OK);
    }
}
