package com.doctor.website.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.doctor.website.DTO.AdminDashboardResponse;
import com.doctor.website.DTO.AdminDoctorRequest;
import com.doctor.website.DTO.AdminDoctorResponse;
import com.doctor.website.service.AdminService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;


    public AdminController(
            AdminService adminService) {

        this.adminService = adminService;
    }


    // =========================================================
    // DASHBOARD STATISTICS
    // =========================================================

    @GetMapping("/dashboard")
    public ResponseEntity<AdminDashboardResponse>
    getDashboardStats() {

        return ResponseEntity.ok(
                adminService.getDashboardStats()
        );
    }


    // =========================================================
    // CREATE DOCTOR
    // =========================================================

    @PostMapping("/doctors")
    public ResponseEntity<AdminDoctorResponse> createDoctor(
            @Valid @RequestBody AdminDoctorRequest request) {

        AdminDoctorResponse response =
                adminService.createDoctor(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    // =========================================================
    // GET ALL DOCTORS
    // =========================================================

    @GetMapping("/doctors")
    public ResponseEntity<List<AdminDoctorResponse>>
    getAllDoctors() {

        return ResponseEntity.ok(
                adminService.getAllDoctors()
        );
    }


    // =========================================================
    // DEACTIVATE DOCTOR
    // =========================================================

    @PutMapping("/doctors/{doctorId}/deactivate")
    public ResponseEntity<AdminDoctorResponse>
    deactivateDoctor(
            @PathVariable int doctorId) {

        return ResponseEntity.ok(
                adminService.deactivateDoctor(
                        doctorId
                )
        );
    }


    // =========================================================
    // ACTIVATE DOCTOR
    // =========================================================

    @PutMapping("/doctors/{doctorId}/activate")
    public ResponseEntity<AdminDoctorResponse>
    activateDoctor(
            @PathVariable int doctorId) {

        return ResponseEntity.ok(
                adminService.activateDoctor(
                        doctorId
                )
        );
    }
}