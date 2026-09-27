package com.doctor.website.DTO;

import java.time.LocalDate;
import java.time.LocalTime;

import com.doctor.website.enums.AppointmentStatus;

public class AppointmentResponse {

    private int appointmentId;

    private LocalDate appointmentDate;

    private LocalTime appointmentTime;

    private AppointmentStatus status;

    private int patientId;

    private String patientName;
    
    private String patientPhone;

    private int doctorId;

    private String doctorName;

    private int consultationFee;

    private int slotId;


    // =========================================================
    // APPOINTMENT ID
    // =========================================================

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }


    // =========================================================
    // APPOINTMENT DATE
    // =========================================================

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }


    // =========================================================
    // APPOINTMENT TIME
    // =========================================================

    public LocalTime getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(LocalTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }


    // =========================================================
    // STATUS
    // =========================================================

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }


    // =========================================================
    // PATIENT
    // =========================================================

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }
    
    public void setPatientPhone(String patientPhone) {
        this.patientPhone = patientPhone;
    }


    // =========================================================
    // DOCTOR
    // =========================================================

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }


    // =========================================================
    // CONSULTATION FEE
    // =========================================================

    public int getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(int consultationFee) {
        this.consultationFee = consultationFee;
    }


    // =========================================================
    // SLOT
    // =========================================================

    public int getSlotId() {
        return slotId;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }
}