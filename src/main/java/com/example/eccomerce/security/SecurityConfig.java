package com.example.eccomerce.security;

import com.example.eccomerce.security.filter.JwtTokenValidator;
import com.example.eccomerce.security.jwt.JwtUtils;
import com.example.eccomerce.service.impl.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtils jwtUtils;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
            .cors(cors -> cors
                    .configurationSource(request -> {
                        var corsConfig = new org.springframework.web.cors.CorsConfiguration();
                        corsConfig.setAllowedOrigins(List.of("http://127.0.0.1:5500")); // origen del frontend
                        corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                        corsConfig.setAllowedHeaders(List.of("*"));
                        corsConfig.setAllowCredentials(true);
                        return corsConfig;
                    })
            )
            .headers(headers -> headers
                    // Content Security Policy (CSP)
                    .contentSecurityPolicy(csp -> csp
                            .policyDirectives("default-src 'self'; script-src 'self';")
                    )
                    // Protección contra clickjacking
                    .frameOptions(frame -> frame
                            .deny()
                    )
            )
            .csrf(csrf -> csrf
                    .disable()
            )
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(
                            "/register",
                            "/log-in",
                            "/recuperar-contraseña/**",
                            "/validation/**",
                            "/swagger-ui/**",
                            "/v3/api-docs/**"
                    ).permitAll()
                    .requestMatchers("/css/**", "/html/**", "/js/**").permitAll()
                    .anyRequest().authenticated()
            )
//            .oauth2Login(oauth2 -> oauth2
//                    .loginPage("/html/login-register.html")
//                    .successHandler(oAuth2AuthenticationSuccessHandler)
//                    .failureUrl("/html/login-register.html?error=oauth2_error")
//            )
            .logout(logout -> {
                logout.logoutUrl("/logout")
                        .deleteCookies("token", "JSESSIONID")
                        .logoutSuccessUrl("/login?logout=success")
                        .invalidateHttpSession(true);
            })

            .formLogin(form -> form
                    .loginPage("/html/login-register.html")
                    .loginProcessingUrl("/html/login-register.html")
            )
            .sessionManagement(sesison->sesison.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(new JwtTokenValidator(jwtUtils), BasicAuthenticationFilter.class)
            .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return  authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsServiceImpl userDetailsService){
        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
