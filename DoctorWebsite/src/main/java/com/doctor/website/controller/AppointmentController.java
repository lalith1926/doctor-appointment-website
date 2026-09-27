package com.doctor.website.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.doctor.website.DTO.AppointmentResponse;
import com.doctor.website.service.AppointmentService;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(
            AppointmentService appointmentService) {

        this.appointmentService = appointmentService;
    }


    // ============================================================
    // PATIENT BOOK APPOINTMENT
    // ============================================================

    @PostMapping("/book")
    public ResponseEntity<AppointmentResponse> bookAppointment(
            @RequestParam int slotId,
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                appointmentService.bookAppointment(
                        email,
                        slotId
                )
        );
    }


    // ============================================================
    // PATIENT APPOINTMENTS
    // ============================================================

    @GetMapping("/patient")
    public ResponseEntity<List<AppointmentResponse>>
    getPatientAppointments(
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                appointmentService.getPatientAppointments(
                        email
                )
        );
    }


    // ============================================================
    // DOCTOR APPOINTMENTS
    // ============================================================

    @GetMapping("/doctor")
    public ResponseEntity<List<AppointmentResponse>>
    getDoctorAppointments(
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                appointmentService.getDoctorAppointments(
                        email
                )
        );
    }


    // ============================================================
    // DOCTOR CONFIRM APPOINTMENT
    // ============================================================

    @PutMapping("/{appointmentId}/confirm")
    public ResponseEntity<AppointmentResponse>
    confirmAppointment(
            @PathVariable int appointmentId,
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                appointmentService.confirmAppointment(
                        appointmentId,
                        email
                )
        );
    }


    // ============================================================
    // DOCTOR CANCEL APPOINTMENT
    // ============================================================

    @PutMapping("/{appointmentId}/cancel/doctor")
    public ResponseEntity<AppointmentResponse>
    cancelDoctorAppointment(
            @PathVariable int appointmentId,
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                appointmentService.cancelDoctorAppointment(
                        appointmentId,
                        email
                )
        );
    }


    // ============================================================
    // PATIENT CANCEL APPOINTMENT
    // ============================================================

    @PutMapping("/{appointmentId}/cancel/patient")
    public ResponseEntity<AppointmentResponse>
    cancelPatientAppointment(
            @PathVariable int appointmentId,
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                appointmentService.cancelPatientAppointment(
                        appointmentId,
                        email
                )
        );
    }


    // ============================================================
    // DOCTOR COMPLETE APPOINTMENT
    // ============================================================

    @PutMapping("/{appointmentId}/complete")
    public ResponseEntity<AppointmentResponse>
    completeAppointment(
            @PathVariable int appointmentId,
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                appointmentService.completeAppointment(
                        appointmentId,
                        email
                )
        );
    }
}