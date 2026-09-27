package com.doctor.website.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.doctor.website.DTO.DoctorProfileRequest;
import com.doctor.website.DTO.DoctorProfileResponse;
import com.doctor.website.DTO.PatientProfileRequest;
import com.doctor.website.DTO.PatientProfileResponse;
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
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final JwtService jwtService;

    public ProfileServiceImpl(
            UserRepository userRepository,
            PatientRepository patientRepository,
            DoctorRepository doctorRepository,
            JwtService jwtService) {

        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.jwtService = jwtService;
    }

    // =========================================================
    // GET PATIENT PROFILE
    // =========================================================

    @Override
    public PatientProfileResponse getPatientProfile(String currentEmail) {

        User user = userRepository.findByEmail(currentEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        if (user.getRole() != Role.PATIENT) {
            throw new RuntimeException(
                    "Only patient accounts can access a patient profile.");
        }

        Patient patient = patientRepository
                .findByUser_Email(currentEmail)
                .orElse(null);

        if (patient == null) {
            return new PatientProfileResponse(
                    null,
                    user.getId(),
                    0,
                    user.getName(),
                    user.getEmail(),
                    user.getPhone(),
                    0,
                    "",
                    "",
                    ""
            );
        }

        return new PatientProfileResponse(
                null,
                user.getId(),
                patient.getP_id(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                patient.getP_age(),
                patient.getP_gender(),
                patient.getP_address(),
                patient.getP_bloodGroup()
        );
    }

    // =========================================================
    // UPDATE PATIENT PROFILE
    // =========================================================

    @Override
    @Transactional
    public PatientProfileResponse updatePatientProfile(
            String currentEmail,
            PatientProfileRequest request) {

        User user = userRepository.findByEmail(currentEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        if (user.getRole() != Role.PATIENT) {
            throw new RuntimeException(
                    "Only patient accounts can update a patient profile.");
        }

        // =====================================================
        // FIND EXISTING PATIENT BEFORE CHANGING EMAIL
        // =====================================================

        Patient patient = patientRepository
                .findByUser_Email(currentEmail)
                .orElse(null);

        // =====================================================
        // CHECK NEW EMAIL
        // =====================================================

        if (!currentEmail.equals(request.getEmail())
                && userRepository.existsByEmail(request.getEmail())) {

            throw new RuntimeException("Email already registered.");
        }

        // =====================================================
        // UPDATE USER
        // =====================================================

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());

        User savedUser = userRepository.save(user);

        // =====================================================
        // GET OR CREATE PATIENT PROFILE
        // =====================================================

        if (patient == null) {
            patient = new Patient();
            patient.setUser(savedUser);
        } else {
            // Make sure the existing patient remains connected
            // to the updated user.
            patient.setUser(savedUser);
        }

        // =====================================================
        // UPDATE PATIENT DETAILS
        // =====================================================

        patient.setP_age(request.getAge());
        patient.setP_gender(request.getGender());
        patient.setP_address(request.getAddress());
        patient.setP_bloodGroup(request.getBloodGroup());

        Patient savedPatient = patientRepository.save(patient);

        // =====================================================
        // GENERATE NEW JWT
        // =====================================================

        String token = jwtService.generateToken(
                savedUser.getEmail()
        );

        // =====================================================
        // RETURN UPDATED PROFILE
        // =====================================================

        return new PatientProfileResponse(
                token,
                savedUser.getId(),
                savedPatient.getP_id(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getPhone(),
                savedPatient.getP_age(),
                savedPatient.getP_gender(),
                savedPatient.getP_address(),
                savedPatient.getP_bloodGroup()
        );
    }

    // =========================================================
    // GET DOCTOR PROFILE
    // =========================================================

    @Override
    public DoctorProfileResponse getDoctorProfile(String currentEmail) {

        User user = userRepository.findByEmail(currentEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        if (user.getRole() != Role.DOCTOR) {
            throw new RuntimeException(
                    "Only doctor accounts can access a doctor profile.");
        }

        Doctor doctor = doctorRepository
                .findByUser_Email(currentEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Doctor profile not found"));

        return new DoctorProfileResponse(
                null,
                user.getId(),
                doctor.getD_id(),
                doctor.getD_name(),
                user.getEmail(),
                user.getPhone(),
                doctor.getD_specialization(),
                doctor.getD_experience(),
                doctor.getD_qualification(),
                doctor.getD_hospital(),
                doctor.getD_consultationFee(),
                doctor.getD_availableFrom(),
                doctor.getD_availableTo()
        );
    }

    // =========================================================
    // UPDATE DOCTOR PROFILE
    // =========================================================

    @Override
    @Transactional
    public DoctorProfileResponse updateDoctorProfile(
            String currentEmail,
            DoctorProfileRequest request) {

        User user = userRepository.findByEmail(currentEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        if (user.getRole() != Role.DOCTOR) {
            throw new RuntimeException(
                    "Only doctor accounts can update a doctor profile.");
        }

        // =====================================================
        // FIND EXISTING DOCTOR BEFORE CHANGING EMAIL
        // =====================================================

        Doctor doctor = doctorRepository
                .findByUser_Email(currentEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Doctor profile not found"));

        // =====================================================
        // CHECK NEW EMAIL
        // =====================================================

        if (!currentEmail.equals(request.getEmail())
                && userRepository.existsByEmail(request.getEmail())) {

            throw new RuntimeException("Email already registered.");
        }

        // =====================================================
        // UPDATE USER
        // =====================================================

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());

        User savedUser = userRepository.save(user);

        // =====================================================
        // KEEP DOCTOR CONNECTED TO UPDATED USER
        // =====================================================

        doctor.setUser(savedUser);

        // =====================================================
        // UPDATE DOCTOR DETAILS
        // =====================================================

        doctor.setD_name(request.getName());
        doctor.setD_specialization(request.getSpecialization());
        doctor.setD_experience(request.getExperience());
        doctor.setD_qualification(request.getQualification());
        doctor.setD_hospital(request.getHospital());
        doctor.setD_consultationFee(request.getConsultationFee());
        doctor.setD_availableFrom(request.getAvailableFrom());
        doctor.setD_availableTo(request.getAvailableTo());

        Doctor savedDoctor = doctorRepository.save(doctor);

        // =====================================================
        // GENERATE NEW JWT
        // =====================================================

        String token = jwtService.generateToken(
                savedUser.getEmail()
        );

        // =====================================================
        // RETURN UPDATED PROFILE
        // =====================================================

        return new DoctorProfileResponse(
                token,
                savedUser.getId(),
                savedDoctor.getD_id(),
                savedDoctor.getD_name(),
                savedUser.getEmail(),
                savedUser.getPhone(),
                savedDoctor.getD_specialization(),
                savedDoctor.getD_experience(),
                savedDoctor.getD_qualification(),
                savedDoctor.getD_hospital(),
                savedDoctor.getD_consultationFee(),
                savedDoctor.getD_availableFrom(),
                savedDoctor.getD_availableTo()
        );
    }
}