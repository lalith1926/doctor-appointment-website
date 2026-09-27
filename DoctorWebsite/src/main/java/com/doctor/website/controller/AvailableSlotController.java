package com.doctor.website.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.doctor.website.entity.AvailableSlot;
import com.doctor.website.service.AvailableSlotService;

@RestController
@RequestMapping("/api/slots")
public class AvailableSlotController {

    private final AvailableSlotService slotService;


    public AvailableSlotController(
            AvailableSlotService slotService) {

        this.slotService = slotService;
    }


    // =========================================================
    // DOCTOR CREATES SLOT
    // =========================================================

    @PostMapping
    public ResponseEntity<AvailableSlot> createSlot(
            @RequestParam LocalDate date,
            @RequestParam LocalTime startTime,
            @RequestParam LocalTime endTime,
            Authentication authentication) {

        String email =
                authentication.getName();


        return ResponseEntity.ok(
                slotService.createSlot(
                        email,
                        date,
                        startTime,
                        endTime
                )
        );
    }


    // =========================================================
    // DOCTOR GETS OWN SLOTS
    // =========================================================

    @GetMapping("/doctor")
    public ResponseEntity<List<AvailableSlot>> getDoctorSlots(
            @RequestParam LocalDate date,
            Authentication authentication) {

        String email =
                authentication.getName();


        return ResponseEntity.ok(
                slotService.getSlots(
                        email,
                        date
                )
        );
    }


    // =========================================================
    // PATIENT GETS DOCTOR SLOTS
    // =========================================================

    @GetMapping
    public ResponseEntity<List<AvailableSlot>> getSlots(
            @RequestParam int doctorId,
            @RequestParam LocalDate date) {

        return ResponseEntity.ok(
                slotService.getSlotsForDoctor(
                        doctorId,
                        date
                )
        );
    }
}