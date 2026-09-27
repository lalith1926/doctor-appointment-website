package com.doctor.website.service;

import java.util.List;

import com.doctor.website.DTO.AdminDashboardResponse;
import com.doctor.website.DTO.AdminDoctorRequest;
import com.doctor.website.DTO.AdminDoctorResponse;

public interface AdminService {

    // =========================================================
    // DASHBOARD
    // =========================================================

    AdminDashboardResponse getDashboardStats();


    // =========================================================
    // DOCTORS
    // =========================================================

    AdminDoctorResponse createDoctor(
            AdminDoctorRequest request
    );

    List<AdminDoctorResponse> getAllDoctors();

    AdminDoctorResponse deactivateDoctor(
            int doctorId
    );

    AdminDoctorResponse activateDoctor(
            int doctorId
    );
}