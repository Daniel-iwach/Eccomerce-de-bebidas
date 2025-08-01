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

/**
 * Service class that provides user authentication functionality, including login and user details loading.
 * Implements the UserDetailsService interface for Spring Security.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private JwtUtils jwtUtils;  // Utility for handling JWT creation and validation

    @Autowired
    private PasswordEncoder passwordEncoder;  // Encoder for password verification

    @Autowired
    private UserRepository userRepository;  // Repository to interact with the User data

    /**
     * Loads user details from the database based on the provided username.
     *
     * @param username the username of the user to load
     * @return the UserDetails object containing user information and authorities
     * @throws UsernameNotFoundException if the user with the given username is not found
     */
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

    /**
     * Logs in the user and returns an AuthResponse containing JWT token and user roles.
     *
     * @param authLoginRequest the login request containing username and password
     * @return an AuthResponse object with login details
     */
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

    /**
     * Authenticates the user by comparing the provided credentials with the stored data.
     *
     * @param password the password to authenticate
     * @return an Authentication object containing authenticated user details
     * @throws BadCredentialsException if the credentials are invalid
     */
    public Authentication authenticate(String email, String password) {
        UserDetails userDetails = this.loadUserByUsername(email);

        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException("The user with email: " + email + " does not exist"));

        // Check if the user exists and if the password matches
        if (userDetails == null || !passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid email or password");
        }

        if (userEntity.getState().equals(EUserState.ACTIVADO)){
            // Return the authentication token
            return new UsernamePasswordAuthenticationToken(userEntity.getName(), password, userDetails.getAuthorities());
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
