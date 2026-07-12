package com.govind.cardealershipinventory.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    	http
        .csrf(csrf -> csrf.disable())
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .sessionManagement(session ->
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth

            // Public endpoints
            .requestMatchers("/api/auth/register", "/api/auth/login").permitAll()

            // Add vehicle - ADMIN only
            .requestMatchers(HttpMethod.POST, "/api/vehicles").hasRole("ADMIN")

            // Update vehicle - ADMIN only
            .requestMatchers(HttpMethod.PUT, "/api/vehicles/**").hasRole("ADMIN")

            // Delete vehicle - ADMIN only
            .requestMatchers(HttpMethod.DELETE, "/api/vehicles/**").hasRole("ADMIN")

            // Restock vehicle - ADMIN only
            .requestMatchers(HttpMethod.POST, "/api/vehicles/*/restock").hasRole("ADMIN")

            // Purchase vehicle - Any authenticated user
            .requestMatchers(HttpMethod.POST, "/api/vehicles/*/purchase").authenticated()

            // View/search vehicles - Any authenticated user
            .requestMatchers(HttpMethod.GET, "/api/vehicles/**").authenticated()

            // Everything else requires authentication
            .anyRequest().authenticated()
        )
        .addFilterBefore(
            jwtAuthenticationFilter,
            UsernamePasswordAuthenticationFilter.class
        );
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}