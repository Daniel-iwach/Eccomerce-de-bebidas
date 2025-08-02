package com.example.eccomerce.controller;


import com.example.eccomerce.model.dtos.request.AuthLoginRequest;
import com.example.eccomerce.model.dtos.request.RequestUserRegisterDto;
import com.example.eccomerce.model.dtos.response.AuthResponse;
import com.example.eccomerce.model.dtos.response.ResponseUserDto;
import com.example.eccomerce.service.impl.UserDetailsServiceImpl;
import com.example.eccomerce.service.interfaces.IUserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final UserDetailsServiceImpl userDetailService;

    private final IUserService userService;

    @PostMapping("/log-in")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthLoginRequest userRequest, HttpServletResponse response) {
        return new ResponseEntity<>(this.userDetailService.loginUser(userRequest,response), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseUserDto> create(@RequestBody @Valid RequestUserRegisterDto user) {
        return new ResponseEntity<>(userService.create(user), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        userDetailService.deleteCookie(response);
        return ResponseEntity.ok("Sesión cerrada");
    }



}
