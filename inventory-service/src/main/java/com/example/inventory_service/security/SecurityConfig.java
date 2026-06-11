package com.example.inventory_service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private JwtAuthConverter jwtAuthConverter;

    //configurer un Resource Server Spring Boot sécurisé avec JWT (OAuth 2.0 / OpenID Connect), et tu utilises un SecurityFilterChain moderne avec Spring Security
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(Customizer.withDefaults())
                //.authorizeHttpRequests(ar->ar.requestMatchers("/products/**").permitAll())
                .authorizeHttpRequests(ar->ar.anyRequest().authenticated())
                //actives le mode Resource Server basé sur JWT (OAuth 2.0),et tu précises un jwtAuthenticationConverter
                // jwtAuthenticationConverter=>(Extraire les rôles ou claims du token JWT et les convertir en GrantedAuthorities Spring Security.)
                .oauth2ResourceServer(o2->o2.jwt(jwt->jwt.jwtAuthenticationConverter(jwtAuthConverter)))
                .headers(h->h.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .csrf(csrf->csrf.ignoringRequestMatchers("/h2-console/**"))
                //.exceptionHandling(eh->eh.accessDeniedPage("/notAuthorized"))
                .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
