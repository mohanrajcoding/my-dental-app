package com.auth_service.config;

import com.auth_service.exception.BusinessException;
import com.auth_service.logging.LogManager;
import com.auth_service.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final LogManager logManager;
    //private static final Logger logger = LoggerFactory.getLogger("APP-DEBUG");
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            logManager.logDebug("SecurityFilterChain:: Inside SecurityFilterChain");
            http
                    .csrf(AbstractHttpConfigurer::disable)
                    .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .authorizeHttpRequests(auth -> auth.requestMatchers("/api/auth/**").permitAll()
                            .requestMatchers("/api/admin/**").hasRole("ADMIN").anyRequest().authenticated())
                    .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
            return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
            return configuration.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
