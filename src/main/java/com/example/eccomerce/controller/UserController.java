package com.example.eccomerce.controller;

import com.example.eccomerce.model.dtos.request.RequestUserRegisterDto;
import com.example.eccomerce.model.dtos.response.ResponseUserDto;
import com.example.eccomerce.service.impl.UserServiceImpl;
import com.example.eccomerce.service.interfaces.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;


    @GetMapping("/getAll")
    public ResponseEntity<List<ResponseUserDto>> getAll() {
        return new ResponseEntity<>(userService.listAll(), HttpStatus.OK);
    }

    @GetMapping("/get-by-id/{userId}")
    public ResponseEntity<ResponseUserDto> getUserById(@PathVariable String userId) {
        return new ResponseEntity<>(userService.findById(userId), HttpStatus.OK);
    }

    @GetMapping("/get-by-email/{email}")
    public ResponseEntity<ResponseUserDto> getUserByEmail(@PathVariable String email) {
        return new ResponseEntity<>(userService.findByEmail(email), HttpStatus.OK);
    }
}
