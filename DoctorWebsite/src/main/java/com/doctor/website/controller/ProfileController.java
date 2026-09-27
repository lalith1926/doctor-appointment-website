package com.doctor.website.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.doctor.website.DTO.DoctorProfileRequest;
import com.doctor.website.DTO.DoctorProfileResponse;
import com.doctor.website.DTO.PatientProfileRequest;
import com.doctor.website.DTO.PatientProfileResponse;
import com.doctor.website.service.ProfileService;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final ProfileService profileService;


    public ProfileController(
            ProfileService profileService) {

        this.profileService = profileService;
    }


    // =========================================================
    // GET PATIENT PROFILE
    // =========================================================

    @GetMapping("/patient")
    public ResponseEntity<PatientProfileResponse>
    getPatientProfile(
            Authentication authentication) {

        return ResponseEntity.ok(
                profileService.getPatientProfile(
                        authentication.getName()
                )
        );
    }


    // =========================================================
    // UPDATE PATIENT PROFILE
    // =========================================================

    @PutMapping("/patient")
    public ResponseEntity<PatientProfileResponse>
    updatePatientProfile(
            Authentication authentication,
            @RequestBody PatientProfileRequest request) {

        return ResponseEntity.ok(
                profileService.updatePatientProfile(
                        authentication.getName(),
                        request
                )
        );
    }


    // =========================================================
    // GET DOCTOR PROFILE
    // =========================================================

    @GetMapping("/doctor")
    public ResponseEntity<DoctorProfileResponse>
    getDoctorProfile(
            Authentication authentication) {

        return ResponseEntity.ok(
                profileService.getDoctorProfile(
                        authentication.getName()
                )
        );
    }


    // =========================================================
    // UPDATE DOCTOR PROFILE
    // =========================================================

    @PutMapping("/doctor")
    public ResponseEntity<DoctorProfileResponse>
    updateDoctorProfile(
            Authentication authentication,
            @RequestBody DoctorProfileRequest request) {

        return ResponseEntity.ok(
                profileService.updateDoctorProfile(
                        authentication.getName(),
                        request
                )
        );
    }
}