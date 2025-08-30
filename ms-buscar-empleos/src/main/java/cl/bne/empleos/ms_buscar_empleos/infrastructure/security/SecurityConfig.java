package cl.bne.empleos.ms_buscar_empleos.infrastructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Desactiva CSRF (para pruebas con Postman o navegador)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/**").permitAll() // Permitir acceso libre a /api/**
                .anyRequest().authenticated()
            )
            .httpBasic(); // Autenticación básica si la necesitas

        return http.build();
    }
}
