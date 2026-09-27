package com.doctor.website.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.doctor.website.security.JwtAuthenticationFilter;
import com.doctor.website.service.CustomUserDetailsService;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration =
                new CorsConfiguration();

        configuration.setAllowedOrigins(
                List.of("http://localhost:4200")
        );

        configuration.setAllowedMethods(
                List.of(
                        "GET",
                        "POST",
                        "PUT",
                        "DELETE",
                        "OPTIONS"
                )
        );

        configuration.setAllowedHeaders(
                List.of("*")
        );

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration(
                "/**",
                configuration
        );

        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            DaoAuthenticationProvider authenticationProvider)
            throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .cors(cors -> {})

            .authenticationProvider(authenticationProvider)

            .sessionManagement(session ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            )

            .authorizeHttpRequests(auth -> auth

                // =========================
                // CORS PREFLIGHT
                // =========================

                .requestMatchers(
                    HttpMethod.OPTIONS,
                    "/**"
                )
                .permitAll()


                // =========================
                // AUTHENTICATION
                // =========================

                .requestMatchers(
                    "/api/auth/login",
                    "/api/auth/register"
                )
                .permitAll()


                // =========================
                // CURRENT USER
                // =========================

                .requestMatchers(
                    "/api/auth/me"
                )
                .authenticated()


                // =========================
                // ADMIN
                // =========================

                /*
                 * Everything under /api/admin/
                 * can only be accessed by ADMIN.
                 */

                .requestMatchers(
                    "/api/admin/**"
                )
                .hasRole("ADMIN")
                
             // =========================
             // DOCTORS
             // =========================

             // Patients + Doctors can view active doctors
             .requestMatchers(
                 HttpMethod.GET,
                 "/api/doctors"
             )
             .hasAnyRole(
                 "PATIENT",
                 "DOCTOR"
             )


                // =========================
                // SLOTS
                // =========================

                // Patient + Doctor can VIEW slots
                .requestMatchers(
                    HttpMethod.GET,
                    "/api/slots"
                )
                .hasAnyRole(
                    "PATIENT",
                    "DOCTOR"
                )


                // Only Doctor can CREATE slots
                .requestMatchers(
                    HttpMethod.POST,
                    "/api/slots"
                )
                .hasRole("DOCTOR")


                // =========================
                // APPOINTMENTS - DOCTOR
                // =========================

                .requestMatchers(
                    "/api/appointments/doctor",
                    "/api/appointments/*/confirm",
                    "/api/appointments/*/complete",
                    "/api/appointments/*/cancel/doctor"
                )
                .hasRole("DOCTOR")


                // =========================
                // APPOINTMENTS - PATIENT
                // =========================

                .requestMatchers(
                    "/api/appointments/book",
                    "/api/appointments/patient",
                    "/api/appointments/*/cancel/patient"
                )
                .hasRole("PATIENT")


                // =========================
                // EVERYTHING ELSE
                // =========================

                .anyRequest()
                .authenticated()
            )

            .addFilterBefore(
                jwtFilter,
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider(
            CustomUserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();

        provider.setUserDetailsService(
                userDetailsService
        );

        provider.setPasswordEncoder(
                passwordEncoder
        );

        return provider;
    }


    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        return configuration.getAuthenticationManager();
    }

}