package com.example.eccomerce.service.impl;


import com.example.eccomerce.exception.UserNotActiveException;
import com.example.eccomerce.model.UserEntity;
import com.example.eccomerce.model.dtos.request.AuthLoginRequest;
import com.example.eccomerce.model.dtos.response.AuthResponse;
import com.example.eccomerce.model.enums.EUserState;
import com.example.eccomerce.repository.UserRepository;
import com.example.eccomerce.security.jwt.JwtUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final JwtUtils jwtUtils;  // Utility for handling JWT creation and validation

    private final PasswordEncoder passwordEncoder;  // Encoder for password verification

    private final UserRepository userRepository;  // Repository to interact with the User data

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("The user with email: " + username + " does not exist"));

        // Map roles to GrantedAuthority objects
        Collection<? extends GrantedAuthority> authorities = userEntity.getRoleList().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toSet());

        // Return User object (from Spring Security) with user credentials and authorities
        return new User(userEntity.getName(),
                userEntity.getPassword(),
                true, true, true, true, authorities);
    }

    public AuthResponse loginUser(AuthLoginRequest authLoginRequest, HttpServletResponse response) {

        String email = authLoginRequest.email().toLowerCase();
        String password = authLoginRequest.password();

        // Authenticate the user
        Authentication authentication = this.authenticate(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Create JWT token
        String accessToken = jwtUtils.createToken(authentication);

        // Configurar cookie JWT
        Cookie jwtCookie = new Cookie("token", accessToken);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(false); // Cambiar a true en producción con HTTPS
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(60 * 60); // 1 hora

        response.addCookie(jwtCookie);

        // Limpiar cualquier cookie de sesión anterior que pueda causar conflictos
        Cookie sessionCookie = new Cookie("JSESSIONID", "");
        sessionCookie.setPath("/");
        sessionCookie.setMaxAge(0);
        response.addCookie(sessionCookie);

        // Get roles from the authentication object
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        // Return authentication response
        return new AuthResponse("User logged successfully",true, roles);
    }


    public Authentication authenticate(String email, String password) {
        UserDetails userDetails = this.loadUserByUsername(email);

        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException("The user with email: " + email + " does not exist"));

        // Check if the user exists and if the password matches
        if (userDetails == null || !passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid email or password");
        }

        if (userEntity.getState().equals(EUserState.ACTIVADO)){    //esta para que no detecte el estado (CAMBIALO)
            // Return the authentication token
            return new UsernamePasswordAuthenticationToken(userEntity.getEmail(), password, userDetails.getAuthorities());
        }else {
            throw new UserNotActiveException(email);
        }
    }

    public void deleteCookie(HttpServletResponse response){
        Cookie jwtCookie = new Cookie("token", "");
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(false);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(0);

        response.addCookie(jwtCookie);
    }
}
