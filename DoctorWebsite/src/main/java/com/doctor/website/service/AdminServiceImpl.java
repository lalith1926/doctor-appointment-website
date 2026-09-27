package com.doctor.website.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.doctor.website.DTO.AdminDashboardResponse;
import com.doctor.website.DTO.AdminDoctorRequest;
import com.doctor.website.DTO.AdminDoctorResponse;
import com.doctor.website.entity.Doctor;
import com.doctor.website.entity.User;
import com.doctor.website.enums.Role;
import com.doctor.website.repository.AppointmentRepository;
import com.doctor.website.repository.DoctorRepository;
import com.doctor.website.repository.PatientRepository;
import com.doctor.website.repository.UserRepository;

@Service
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;

    private final DoctorRepository doctorRepository;

    private final PatientRepository patientRepository;

    private final AppointmentRepository appointmentRepository;

    private final PasswordEncoder passwordEncoder;


    public AdminServiceImpl(
            UserRepository userRepository,
            DoctorRepository doctorRepository,
            PatientRepository patientRepository,
            AppointmentRepository appointmentRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;

        this.doctorRepository = doctorRepository;

        this.patientRepository = patientRepository;

        this.appointmentRepository = appointmentRepository;

        this.passwordEncoder = passwordEncoder;
    }


    // =========================================================
    // DASHBOARD STATISTICS
    // =========================================================

    @Override
    public AdminDashboardResponse getDashboardStats() {

        long doctorCount =
                doctorRepository.count();

        long patientCount =
                patientRepository.count();

        long appointmentCount =
                appointmentRepository.count();

        return new AdminDashboardResponse(
                doctorCount,
                patientCount,
                appointmentCount
        );
    }


    // =========================================================
    // CREATE DOCTOR
    // =========================================================

    @Override
    @Transactional
    public AdminDoctorResponse createDoctor(
            AdminDoctorRequest request) {

        if (userRepository.existsByEmail(
                request.getEmail())) {

            throw new RuntimeException(
                    "Email already registered."
            );
        }


        User user = new User();

        user.setName(request.getName());

        user.setEmail(request.getEmail());

        user.setPhone(request.getPhone());

        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );

        user.setRole(Role.DOCTOR);

        user.setActive(true);


        User savedUser =
                userRepository.save(user);


        Doctor doctor = new Doctor();

        doctor.setUser(savedUser);

        doctor.setD_name(
                request.getName()
        );

        doctor.setD_specialization(
                request.getSpecialization()
        );

        doctor.setD_experience(
                request.getExperience()
        );

        doctor.setD_qualification(
                request.getQualification()
        );

        doctor.setD_hospital(
                request.getHospital()
        );

        doctor.setD_consultationFee(
                request.getConsultationFee()
        );

        doctor.setD_availableFrom(
                request.getAvailableFrom()
        );

        doctor.setD_availableTo(
                request.getAvailableTo()
        );


        Doctor savedDoctor =
                doctorRepository.save(doctor);


        return mapToResponse(savedDoctor);
    }


    // =========================================================
    // GET ALL DOCTORS
    // =========================================================

    @Override
    public List<AdminDoctorResponse> getAllDoctors() {

        return doctorRepository
                .findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    // =========================================================
    // DEACTIVATE DOCTOR
    // =========================================================

    @Override
    @Transactional
    public AdminDoctorResponse deactivateDoctor(
            int doctorId) {

        Doctor doctor =
                doctorRepository
                        .findById(doctorId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Doctor not found."
                                )
                        );


        User user = doctor.getUser();

        user.setActive(false);

        userRepository.save(user);


        return mapToResponse(doctor);
    }


    // =========================================================
    // ACTIVATE DOCTOR
    // =========================================================

    @Override
    @Transactional
    public AdminDoctorResponse activateDoctor(
            int doctorId) {

        Doctor doctor =
                doctorRepository
                        .findById(doctorId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Doctor not found."
                                )
                        );


        User user = doctor.getUser();

        user.setActive(true);

        userRepository.save(user);


        return mapToResponse(doctor);
    }


    // =========================================================
    // MAP DOCTOR RESPONSE
    // =========================================================

    private AdminDoctorResponse mapToResponse(
            Doctor doctor) {

        User user = doctor.getUser();


        long patientCount =
                appointmentRepository
                        .countUniquePatientsByDoctorId(
                                doctor.getD_id()
                        );


        return new AdminDoctorResponse(

                doctor.getD_id(),

                user.getId(),

                doctor.getD_name(),

                user.getEmail(),

                user.getPhone(),

                doctor.getD_specialization(),

                doctor.getD_experience(),

                doctor.getD_qualification(),

                doctor.getD_hospital(),

                doctor.getD_consultationFee(),

                doctor.getD_availableFrom(),

                doctor.getD_availableTo(),

                user.isActive(),

                patientCount
        );
    }
}