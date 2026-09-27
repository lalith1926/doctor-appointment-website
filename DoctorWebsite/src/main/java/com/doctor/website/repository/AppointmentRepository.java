package com.doctor.website.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.doctor.website.entity.Appointment;
import com.doctor.website.entity.Patient;

@Repository
public interface AppointmentRepository
        extends JpaRepository<Appointment, Integer> {

    // =========================================================
    // PATIENT APPOINTMENTS
    // =========================================================

    List<Appointment> findByPatient(
            Patient patient
    );


    // =========================================================
    // DOCTOR APPOINTMENTS
    // =========================================================

    @Query("""
            SELECT a
            FROM Appointment a
            WHERE a.doctor.d_id = :doctorId
            ORDER BY a.appointmentDate ASC,
                     a.appointmentTime ASC
            """)
    List<Appointment> findAppointmentsByDoctorId(
            @Param("doctorId") int doctorId
    );


    // =========================================================
    // UNIQUE PATIENT COUNT FOR DOCTOR
    // =========================================================

    @Query("""
            SELECT COUNT(DISTINCT a.patient.p_id)
            FROM Appointment a
            WHERE a.doctor.d_id = :doctorId
            AND a.status <> com.doctor.website.enums.AppointmentStatus.CANCELLED
            """)
    long countUniquePatientsByDoctorId(
            @Param("doctorId") int doctorId
    );
}