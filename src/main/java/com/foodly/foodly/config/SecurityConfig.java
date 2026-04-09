package com.foodly.foodly.config;

import com.foodly.foodly.security.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(@Lazy JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)

                .authorizeHttpRequests(authorize -> authorize

                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // Rotas públicas
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/produtos/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/categorias/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/restaurantes/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/usuarios/cep/**").permitAll()

                        // ADMIN
                        .requestMatchers(HttpMethod.GET, "/usuarios").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/usuarios/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.POST, "/categorias").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/categorias/**").hasAuthority("ROLE_ADMIN")

                        // ADMIN ou RESTAURANT_OWNER
                        .requestMatchers(HttpMethod.POST, "/produtos").hasAnyAuthority("ROLE_ADMIN", "ROLE_RESTAURANT_OWNER")
                        .requestMatchers(HttpMethod.PUT, "/produtos/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_RESTAURANT_OWNER")
                        .requestMatchers(HttpMethod.DELETE, "/produtos/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_RESTAURANT_OWNER")
                        .requestMatchers(HttpMethod.POST, "/restaurantes").hasAnyAuthority("ROLE_ADMIN", "ROLE_RESTAURANT_OWNER")
                        .requestMatchers(HttpMethod.PUT, "/restaurantes/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_RESTAURANT_OWNER")
                        .requestMatchers(HttpMethod.GET, "/restaurantes/meus").hasAnyAuthority("ROLE_ADMIN", "ROLE_RESTAURANT_OWNER")
                        .requestMatchers(HttpMethod.GET, "/pedidos/restaurante/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_RESTAURANT_OWNER")
                        .requestMatchers(HttpMethod.PATCH, "/pedidos/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_RESTAURANT_OWNER")

                        // ADMIN ou CUSTOMER
                        .requestMatchers(HttpMethod.POST, "/pedidos").hasAnyAuthority("ROLE_ADMIN", "ROLE_CUSTOMER")
                        .requestMatchers(HttpMethod.GET, "/pedidos/meus").hasAnyAuthority("ROLE_ADMIN", "ROLE_CUSTOMER")

                        .anyRequest().authenticated()
                )
                .build();
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:4200"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
            throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}