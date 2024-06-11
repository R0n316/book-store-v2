package ru.alex.bookstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig  {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.formLogin(form -> form
                .loginPage("/auth/login")
                .permitAll()
                .loginProcessingUrl("/auth/login"))
            .authorizeHttpRequests(req -> req
                    .requestMatchers("/auth/**","/styles/**").permitAll()
                    .anyRequest()
//                    .authenticated()
                    .permitAll());

        return http.build();
    }
}
