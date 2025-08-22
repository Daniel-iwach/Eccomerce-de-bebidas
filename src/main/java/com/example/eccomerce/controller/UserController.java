package com.example.eccomerce.controller;

import com.example.eccomerce.model.dtos.request.RequestUserRegisterDto;
import com.example.eccomerce.model.dtos.response.ResponseUserDto;
import com.example.eccomerce.repository.UserRepository;
import com.example.eccomerce.service.impl.UserServiceImpl;
import com.example.eccomerce.service.interfaces.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get-all")
    public ResponseEntity<List<ResponseUserDto>> getAll() {
        return new ResponseEntity<>(userService.listAll(), HttpStatus.OK);
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get-users-activated")
    public ResponseEntity<List<ResponseUserDto>> findUsersActivated(){
        return new ResponseEntity<>(userService.findUsersActivated(),HttpStatus.OK);
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/count-new-users")
    public ResponseEntity<Long> countUsersRegisteredThisMonth(){
        return new ResponseEntity<>(userService.countUsersRegisteredThisMonth(),HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/get-by-id/{userId}")
    public ResponseEntity<ResponseUserDto> getUserById(@PathVariable String userId) {
        return new ResponseEntity<>(userService.findById(userId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/get-by-email/{email}")
    public ResponseEntity<ResponseUserDto> getUserByEmail(@PathVariable String email) {
        return new ResponseEntity<>(userService.findByEmail(email), HttpStatus.OK);
    }
}
