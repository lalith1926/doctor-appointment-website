package com.doctor.website.service;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.stereotype.Service;

import com.doctor.website.entity.User;
import com.doctor.website.repository.UserRepository;

@Service
public class CustomUserDetailsService
        implements UserDetailsService {

    private final UserRepository userRepository;


    public CustomUserDetailsService(
            UserRepository userRepository) {

        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(
            String email)
            throws UsernameNotFoundException {

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found: " + email
                        )
                );


        System.out.println(
                "USER EMAIL = " + user.getEmail()
        );

        System.out.println(
                "USER ROLE = " + user.getRole()
        );

        System.out.println(
                "USER ACTIVE = " + user.isActive()
        );


        // =========================================
        // BLOCK INACTIVE USERS
        // =========================================

        if (!user.isActive()) {

            throw new DisabledException(
                    "This account is inactive."
            );
        }


        return new org.springframework.security.core.userdetails.User(

                user.getEmail(),

                user.getPassword(),

                true,
                true,
                true,
                true,

                List.of(
                        new SimpleGrantedAuthority(
                                "ROLE_" +
                                user.getRole().name()
                        )
                )
        );
    }
}