package com.doctor.website.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.doctor.website.service.CustomUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            CustomUserDetailsService userDetailsService) {

        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String requestUri = request.getRequestURI();

        // Login and registration don't need JWT
        if (requestUri.equals("/api/auth/login")
                || requestUri.equals("/api/auth/register")) {

            filterChain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");

        System.out.println("AUTH HEADER = " + header);

        if (header == null || !header.startsWith("Bearer ")) {

            System.out.println("NO JWT");

            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);

        try {

            String email = jwtService.extractUsername(token);

            System.out.println("JWT EMAIL = " + email);

            UserDetails userDetails =
                    userDetailsService.loadUserByUsername(email);
            
            System.out.println(
                    "USER AUTHORITIES = " +
                    userDetails.getAuthorities()
            );

            boolean valid =
                    jwtService.isTokenValid(token, email);

            System.out.println("JWT VALID = " + valid);

            if (valid) {

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authentication.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );

                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authentication);

                System.out.println("AUTHENTICATION SET");
            }

        } catch (Exception e) {

            System.out.println(
                    "JWT ERROR = " + e.getMessage()
            );

            e.printStackTrace();
        }

        filterChain.doFilter(request, response);
    }
}