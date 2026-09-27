package com.doctor.website.service;

import com.doctor.website.DTO.DoctorProfileRequest;
import com.doctor.website.DTO.DoctorProfileResponse;
import com.doctor.website.DTO.PatientProfileRequest;
import com.doctor.website.DTO.PatientProfileResponse;

public interface ProfileService {

    PatientProfileResponse getPatientProfile(
            String currentEmail
    );

    PatientProfileResponse updatePatientProfile(
            String currentEmail,
            PatientProfileRequest request
    );


    DoctorProfileResponse getDoctorProfile(
            String currentEmail
    );

    DoctorProfileResponse updateDoctorProfile(
            String currentEmail,
            DoctorProfileRequest request
    );
}