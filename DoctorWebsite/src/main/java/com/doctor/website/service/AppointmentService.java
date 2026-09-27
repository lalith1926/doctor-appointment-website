package com.doctor.website.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.doctor.website.DTO.AppointmentResponse;
import com.doctor.website.entity.Appointment;
import com.doctor.website.entity.AvailableSlot;
import com.doctor.website.entity.Doctor;
import com.doctor.website.entity.Patient;
import com.doctor.website.enums.AppointmentStatus;
import com.doctor.website.mapper.AppointmentMapper;
import com.doctor.website.repository.AppointmentRepository;
import com.doctor.website.repository.AvailableSlotRepository;
import com.doctor.website.repository.DoctorRepository;
import com.doctor.website.repository.PatientRepository;

import jakarta.transaction.Transactional;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    private final AvailableSlotRepository slotRepository;

    private final PatientRepository patientRepository;

    private final DoctorRepository doctorRepository;

    private final AppointmentMapper appointmentMapper;

    private final EmailService emailService;

    public AppointmentService(
            AppointmentRepository appointmentRepository,
            AvailableSlotRepository slotRepository,
            PatientRepository patientRepository,
            DoctorRepository doctorRepository,
            AppointmentMapper appointmentMapper,
            EmailService emailService) {

        this.appointmentRepository = appointmentRepository;

        this.slotRepository = slotRepository;

        this.patientRepository = patientRepository;

        this.doctorRepository = doctorRepository;

        this.appointmentMapper = appointmentMapper;

        this.emailService = emailService;
    }

    // =========================================================
    // BOOK APPOINTMENT
    // =========================================================

    @Transactional
    public AppointmentResponse bookAppointment(
            String email,
            int slotId) {

        Patient patient =
                patientRepository.findByUser_Email(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Patient profile not found for user: "
                                        + email
                        )
                );

        AvailableSlot slot =
                slotRepository.findByIdForUpdate(slotId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Appointment slot not found: "
                                        + slotId
                        )
                );

        if (slot.isBooked()) {

            throw new RuntimeException(
                    "Slot is already booked"
            );
        }

        if (slot.getDoctor() == null) {

            throw new RuntimeException(
                    "This slot has no doctor assigned"
            );
        }

        Appointment appointment =
                new Appointment();

        appointment.setPatient(patient);

        appointment.setDoctor(
                slot.getDoctor()
        );

        appointment.setSlot(slot);

        appointment.setAppointmentDate(
                slot.getDate()
        );

        appointment.setAppointmentTime(
                slot.getStartTime()
        );

        appointment.setStatus(
                AppointmentStatus.PENDING
        );

        appointment.setBookedAt(
                LocalDateTime.now()
        );

        Appointment savedAppointment =
                appointmentRepository.save(
                        appointment
                );

        slot.setBooked(true);

        slotRepository.save(slot);

        // =========================================================
        // SEND NEW APPOINTMENT EMAIL TO DOCTOR
        // =========================================================

        System.out.println(
                "========== BEFORE NEW APPOINTMENT EMAIL =========="
        );

        try {

            emailService.sendNewAppointmentEmail(
                    savedAppointment
            );

            System.out.println(
                    "========== NEW APPOINTMENT EMAIL COMPLETED =========="
            );

        } catch (Exception e) {

            System.out.println(
                    "========== NEW APPOINTMENT EMAIL ERROR =========="
            );

            e.printStackTrace();
        }

        return appointmentMapper.toResponse(
                savedAppointment
        );
    }

    // =========================================================
    // GET PATIENT APPOINTMENTS
    // =========================================================

    public List<AppointmentResponse> getPatientAppointments(
            String email) {

        Patient patient =
                patientRepository.findByUser_Email(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Patient profile not found for user: "
                                        + email
                        )
                );

        List<Appointment> appointments =
                appointmentRepository.findByPatient(
                        patient
                );

        return appointments
                .stream()
                .map(appointmentMapper::toResponse)
                .toList();
    }

    // =========================================================
    // GET DOCTOR APPOINTMENTS
    // =========================================================

    public List<AppointmentResponse> getDoctorAppointments(
            String email) {

        Doctor doctor =
                doctorRepository.findByUser_Email(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Doctor profile not found for user: "
                                        + email
                        )
                );

        List<Appointment> appointments =
                appointmentRepository
                        .findAppointmentsByDoctorId(
                                doctor.getD_id()
                        );

        return appointments
                .stream()
                .map(appointmentMapper::toResponse)
                .toList();
    }

    // =========================================================
    // CONFIRM APPOINTMENT
    // =========================================================

    @Transactional
    public AppointmentResponse confirmAppointment(
            int appointmentId,
            String email) {

        Doctor doctor =
                doctorRepository.findByUser_Email(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Doctor profile not found for user: "
                                        + email
                        )
                );

        Appointment appointment =
                appointmentRepository.findById(
                        appointmentId
                )
                .orElseThrow(() ->
                        new RuntimeException(
                                "Appointment not found: "
                                        + appointmentId
                        )
                );

        if (
                appointment.getDoctor() == null ||
                appointment.getDoctor().getD_id()
                        != doctor.getD_id()
        ) {

            throw new RuntimeException(
                    "You can only confirm your own appointments"
            );
        }

        if (
                appointment.getStatus()
                        != AppointmentStatus.PENDING
        ) {

            throw new RuntimeException(
                    "Only pending appointments can be confirmed"
            );
        }

        appointment.setStatus(
                AppointmentStatus.CONFIRMED
        );

        Appointment savedAppointment =
                appointmentRepository.save(
                        appointment
                );

        // =========================================================
        // SEND CONFIRMATION EMAIL TO PATIENT
        // =========================================================

        System.out.println(
                "========== BEFORE APPOINTMENT CONFIRMATION EMAIL =========="
        );

        try {

            emailService.sendAppointmentConfirmationEmail(
                    savedAppointment
            );

            System.out.println(
                    "========== APPOINTMENT CONFIRMATION EMAIL COMPLETED =========="
            );

        } catch (Exception e) {

            System.out.println(
                    "========== APPOINTMENT CONFIRMATION EMAIL ERROR =========="
            );

            e.printStackTrace();
        }

        return appointmentMapper.toResponse(
                savedAppointment
        );
    }

    // =========================================================
    // CANCEL DOCTOR APPOINTMENT
    // =========================================================

    @Transactional
    public AppointmentResponse cancelDoctorAppointment(
            int appointmentId,
            String email) {

        Doctor doctor =
                doctorRepository.findByUser_Email(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Doctor profile not found for user: "
                                        + email
                        )
                );

        Appointment appointment =
                appointmentRepository.findById(
                        appointmentId
                )
                .orElseThrow(() ->
                        new RuntimeException(
                                "Appointment not found: "
                                        + appointmentId
                        )
                );

        if (
                appointment.getDoctor() == null ||
                appointment.getDoctor().getD_id()
                        != doctor.getD_id()
        ) {

            throw new RuntimeException(
                    "You can only cancel your own appointments"
            );
        }

        if (
                appointment.getStatus()
                        == AppointmentStatus.CANCELLED
        ) {

            throw new RuntimeException(
                    "Appointment is already cancelled"
            );
        }

        if (
                appointment.getStatus()
                        == AppointmentStatus.COMPLETED
        ) {

            throw new RuntimeException(
                    "Completed appointment cannot be cancelled"
            );
        }

        AvailableSlot slot =
                appointment.getSlot();

        // =========================================================
        // SEND CANCELLATION EMAIL TO PATIENT
        // =========================================================

        System.out.println(
                "========== BEFORE DOCTOR CANCELLATION EMAIL =========="
        );

        try {

            emailService.sendDoctorCancellationEmail(
                    appointment
            );

            System.out.println(
                    "========== DOCTOR CANCELLATION EMAIL COMPLETED =========="
            );

        } catch (Exception e) {

            System.out.println(
                    "========== DOCTOR CANCELLATION EMAIL ERROR =========="
            );

            e.printStackTrace();
        }

        // =========================================================
        // FREE THE SLOT
        // =========================================================

        if (slot != null) {

            slot.setBooked(false);

            slotRepository.save(slot);
        }

        // =========================================================
        // CANCEL APPOINTMENT
        // =========================================================

        appointment.setStatus(
                AppointmentStatus.CANCELLED
        );

        /*
         * Detach slot because slot_id is UNIQUE
         * in appointments table.
         */

        appointment.setSlot(null);

        return appointmentMapper.toResponse(
                appointmentRepository.save(
                        appointment
                )
        );
    }

    // =========================================================
    // CANCEL PATIENT APPOINTMENT
    // =========================================================

    @Transactional
    public AppointmentResponse cancelPatientAppointment(
            int appointmentId,
            String email) {

        Patient patient =
                patientRepository.findByUser_Email(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Patient profile not found for user: "
                                        + email
                        )
                );

        Appointment appointment =
                appointmentRepository.findById(
                        appointmentId
                )
                .orElseThrow(() ->
                        new RuntimeException(
                                "Appointment not found: "
                                        + appointmentId
                        )
                );

        if (
                appointment.getPatient() == null ||
                appointment.getPatient().getP_id()
                        != patient.getP_id()
        ) {

            throw new RuntimeException(
                    "You can only cancel your own appointment"
            );
        }

        if (
                appointment.getStatus()
                        == AppointmentStatus.CANCELLED
        ) {

            throw new RuntimeException(
                    "Appointment is already cancelled"
            );
        }

        if (
                appointment.getStatus()
                        == AppointmentStatus.COMPLETED
        ) {

            throw new RuntimeException(
                    "Completed appointment cannot be cancelled"
            );
        }

        AvailableSlot slot =
                appointment.getSlot();

        // =========================================================
        // SEND CANCELLATION EMAIL TO DOCTOR
        // =========================================================

        System.out.println(
                "========== BEFORE PATIENT CANCELLATION EMAIL =========="
        );

        try {

            emailService.sendPatientCancellationEmail(
                    appointment
            );

            System.out.println(
                    "========== PATIENT CANCELLATION EMAIL COMPLETED =========="
            );

        } catch (Exception e) {

            System.out.println(
                    "========== PATIENT CANCELLATION EMAIL ERROR =========="
            );

            e.printStackTrace();
        }

        // =========================================================
        // FREE THE SLOT
        // =========================================================

        if (slot != null) {

            slot.setBooked(false);

            slotRepository.save(slot);
        }

        // =========================================================
        // CANCEL APPOINTMENT
        // =========================================================

        appointment.setStatus(
                AppointmentStatus.CANCELLED
        );

        /*
         * Keep cancelled appointment in history,
         * but remove slot relationship so the slot
         * can be booked again.
         */

        appointment.setSlot(null);

        return appointmentMapper.toResponse(
                appointmentRepository.save(
                        appointment
                )
        );
    }

    // =========================================================
    // COMPLETE APPOINTMENT
    // =========================================================

    @Transactional
    public AppointmentResponse completeAppointment(
            int appointmentId,
            String email) {

        Doctor doctor =
                doctorRepository.findByUser_Email(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Doctor profile not found for user: "
                                        + email
                        )
                );

        Appointment appointment =
                appointmentRepository.findById(
                        appointmentId
                )
                .orElseThrow(() ->
                        new RuntimeException(
                                "Appointment not found: "
                                        + appointmentId
                        )
                );

        if (
                appointment.getDoctor() == null ||
                appointment.getDoctor().getD_id()
                        != doctor.getD_id()
        ) {

            throw new RuntimeException(
                    "You can only complete your own appointments"
            );
        }

        if (
                appointment.getStatus()
                        != AppointmentStatus.CONFIRMED
        ) {

            throw new RuntimeException(
                    "Only confirmed appointments can be completed"
            );
        }

        appointment.setStatus(
                AppointmentStatus.COMPLETED
        );

        return appointmentMapper.toResponse(
                appointmentRepository.save(
                        appointment
                )
        );
    }
}