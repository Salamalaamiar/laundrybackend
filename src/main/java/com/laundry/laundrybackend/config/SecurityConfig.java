package com.laundry.laundrybackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for API endpoints
                .cors(cors -> cors.configure(http)) // Enable CORS
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/laundry/send").permitAll() // Allow registration endpoint
                        .requestMatchers("/api/laundry/**").permitAll() // Allow public access to laundry API
                        .requestMatchers("/error").permitAll()// Allow error page
                        .requestMatchers("/api/auth/**").permitAll()      //  Autoriser toutes les routes d'authentification
                        .requestMatchers("/api/orders/**").permitAll()    // Autoriser les routes de commande
                        .requestMatchers("/api/fournisseur/auth/login").permitAll()

                        .requestMatchers("/api/laundries/**").permitAll()

                        .requestMatchers(
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()

                        .anyRequest().authenticated() // Require authentication for other endpoints
                )
                .httpBasic(httpBasic -> httpBasic.disable()) // Disable basic auth
                .formLogin(form -> form.disable()); // Disable form login

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}