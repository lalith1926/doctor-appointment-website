package com.doctor.website.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.doctor.website.DTO.AuthResponse;
import com.doctor.website.DTO.LoginRequest;
import com.doctor.website.DTO.RegisterRequest;
import com.doctor.website.DTO.UserResponse;
import com.doctor.website.entity.Doctor;
import com.doctor.website.entity.Patient;
import com.doctor.website.entity.User;
import com.doctor.website.enums.Role;
import com.doctor.website.exception.ResourceNotFoundException;
import com.doctor.website.repository.DoctorRepository;
import com.doctor.website.repository.PatientRepository;
import com.doctor.website.repository.UserRepository;
import com.doctor.website.security.JwtService;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(
            UserRepository userRepository,
            DoctorRepository doctorRepository,
            PatientRepository patientRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager) {

        this.userRepository = userRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public UserResponse getCurrentUser(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"));

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getRole().name()
        );
    }

    @Override
    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException(
                    "Email already registered.");
        }

        // =========================
        // CREATE USER
        // =========================

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());

        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );

        /*
         * SECURITY:
         *
         * Public registration is ALWAYS PATIENT.
         *
         * We intentionally do NOT use:
         *
         * request.getRole()
         *
         * here.
         */

        user.setRole(Role.PATIENT);

        User savedUser = userRepository.save(user);

        // =========================
        // CREATE PATIENT PROFILE
        // =========================

        Patient patient = new Patient();

        patient.setUser(savedUser);

        // Default patient profile values.
        // The patient can update these later
        // from Edit Profile.

        patient.setP_age(0);
        patient.setP_gender("");
        patient.setP_address("");
        patient.setP_bloodGroup("");

        patientRepository.save(patient);

        // =========================
        // GENERATE JWT
        // =========================

        String token =
                jwtService.generateToken(
                        savedUser.getEmail()
                );

        return new AuthResponse(token);
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        System.out.println("LOGIN API HIT");

        System.out.println(
                "EMAIL = " + request.getEmail()
        );

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user =
                userRepository
                        .findByEmail(
                                request.getEmail()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                )
                        );

        // =========================
        // DOCTOR PROFILE SAFETY CHECK
        // =========================

        /*
         * Keep this because existing DOCTOR accounts
         * may need their Doctor profile created.
         */

        if (user.getRole() == Role.DOCTOR) {

            boolean doctorExists =
                    doctorRepository
                            .findByUser_Email(
                                    user.getEmail()
                            )
                            .isPresent();

            if (!doctorExists) {

                Doctor doctor = new Doctor();

                doctor.setUser(user);

                doctor.setD_name(
                        user.getName()
                );

                doctor.setD_specialization(
                        "Orthopedics"
                );

                doctor.setD_experience(
                        "Not specified"
                );

                doctor.setD_qualification(
                        "MBBS"
                );

                doctor.setD_hospital(
                        "Shyam Ortho Care"
                );

                doctor.setD_consultationFee(
                        500
                );

                doctor.setD_availableFrom(
                        "09:00"
                );

                doctor.setD_availableTo(
                        "17:00"
                );

                doctorRepository.save(doctor);
            }
        }

        // =========================
        // PATIENT PROFILE SAFETY CHECK
        // =========================

        /*
         * This also protects older PATIENT accounts
         * that were created before automatic Patient
         * profile creation was added.
         */

        if (user.getRole() == Role.PATIENT) {

            boolean patientExists =
                    patientRepository
                            .findByUser_Email(
                                    user.getEmail()
                            )
                            .isPresent();

            if (!patientExists) {

                Patient patient = new Patient();

                patient.setUser(user);

                patient.setP_age(0);
                patient.setP_gender("");
                patient.setP_address("");
                patient.setP_bloodGroup("");

                patientRepository.save(patient);
            }
        }

        // =========================
        // GENERATE JWT
        // =========================

        String token =
                jwtService.generateToken(
                        user.getEmail()
                );

        return new AuthResponse(token);
    }
}