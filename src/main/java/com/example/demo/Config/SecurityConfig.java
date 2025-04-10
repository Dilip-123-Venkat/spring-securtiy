package com.example.demo.Config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;

@Configuration
public class SecurityConfig {
    @Autowired
    private JWTFilter jwtFilter;
// configure your securitychain

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().disable();

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/auth/signup", "/api/v1/auth/signin").permitAll()
                .anyRequest().authenticated()
        );

        http.addFilterBefore(jwtFilter, AuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



}
