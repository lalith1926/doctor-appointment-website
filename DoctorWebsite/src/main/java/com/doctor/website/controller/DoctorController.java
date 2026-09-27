package com.doctor.website.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.doctor.website.DTO.DoctorResponse;
import com.doctor.website.service.DoctorService;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;


    public DoctorController(
            DoctorService doctorService) {

        this.doctorService =
                doctorService;
    }


    // =========================================================
    // GET ACTIVE DOCTORS
    // =========================================================

    @GetMapping
    public ResponseEntity<List<DoctorResponse>>
            getActiveDoctors() {

        return ResponseEntity.ok(
                doctorService.getActiveDoctors()
        );
    }
}