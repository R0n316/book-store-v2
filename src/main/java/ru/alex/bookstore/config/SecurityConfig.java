package ru.alex.bookstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig  {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"));
        http.formLogin(form -> form
                .loginPage("/auth/login")
                .loginProcessingUrl("/auth/login"))
            .logout(config -> config
                    .logoutUrl("/auth/logout")
                    .logoutSuccessUrl("/"))
            .authorizeHttpRequests(urlConfig -> urlConfig
                    .requestMatchers("/books/favorites","/books/cart","/api/reviews/**").authenticated()
                    .requestMatchers("/auth/**","/styles/**").permitAll()
                    .anyRequest()
                    .permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
