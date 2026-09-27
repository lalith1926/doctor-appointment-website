package com.doctor.website.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.doctor.website.entity.AvailableSlot;
import com.doctor.website.entity.Doctor;
import com.doctor.website.repository.AvailableSlotRepository;
import com.doctor.website.repository.DoctorRepository;

@Service
public class AvailableSlotService {

    private final AvailableSlotRepository slotRepository;
    private final DoctorRepository doctorRepository;


    public AvailableSlotService(
            AvailableSlotRepository slotRepository,
            DoctorRepository doctorRepository) {

        this.slotRepository = slotRepository;
        this.doctorRepository = doctorRepository;
    }


    // =========================================================
    // CREATE SLOT FOR LOGGED-IN DOCTOR
    // =========================================================

    public AvailableSlot createSlot(
            String email,
            LocalDate date,
            LocalTime startTime,
            LocalTime endTime) {

        Doctor doctor = doctorRepository
                .findByUser_Email(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Doctor profile not found for user: "
                                        + email
                        )
                );


        AvailableSlot slot = new AvailableSlot();

        slot.setDoctor(doctor);

        slot.setDate(date);

        slot.setStartTime(startTime);

        slot.setEndTime(endTime);

        slot.setBooked(false);


        return slotRepository.save(slot);
    }


    // =========================================================
    // GET SLOTS FOR LOGGED-IN DOCTOR
    // =========================================================

    public List<AvailableSlot> getSlots(
            String email,
            LocalDate date) {

        Doctor doctor = doctorRepository
                .findByUser_Email(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Doctor profile not found for user: "
                                        + email
                        )
                );


        return slotRepository
                .findSlotsByDoctorAndDate(
                        doctor.getD_id(),
                        date
                );
    }


    // =========================================================
    // GET SLOTS FOR A PATIENT
    // =========================================================
    //
    // Patients need a doctor ID because they are viewing
    // another person's available slots.
    //

    public List<AvailableSlot> getSlotsForDoctor(
            int doctorId,
            LocalDate date) {

        return slotRepository
                .findSlotsByDoctorAndDate(
                        doctorId,
                        date
                );
    }
}