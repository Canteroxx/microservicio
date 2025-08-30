package cl.bne.empleos.ms_buscar_empleos.infrastructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf
                .ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")) 
                .disable()
            )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/**").permitAll()          
                .requestMatchers("/h2-console/**").permitAll()  
                .anyRequest().authenticated()
            )
            .headers(headers -> headers.frameOptions(frame -> frame.disable())) 
            .httpBasic(); 

        return http.build();
    }
}
