package com.doctor.website.mapper;

import org.springframework.stereotype.Component;

import com.doctor.website.DTO.AppointmentResponse;
import com.doctor.website.entity.Appointment;
import com.doctor.website.entity.Doctor;
import com.doctor.website.entity.Patient;

@Component
public class AppointmentMapper {

    public AppointmentResponse toResponse(
            Appointment appointment) {

        if (appointment == null) {
            return null;
        }

        AppointmentResponse response =
                new AppointmentResponse();


        // =====================================================
        // APPOINTMENT INFORMATION
        // =====================================================

        response.setAppointmentId(
                appointment.getA_id()
        );

        response.setAppointmentDate(
                appointment.getAppointmentDate()
        );

        response.setAppointmentTime(
                appointment.getAppointmentTime()
        );

        response.setStatus(
                appointment.getStatus()
        );


        // =====================================================
        // DOCTOR INFORMATION
        // =====================================================

        Doctor doctor =
                appointment.getDoctor();

        if (doctor != null) {

            response.setDoctorId(
                    doctor.getD_id()
            );

            response.setDoctorName(
                    doctor.getD_name()
            );

            response.setConsultationFee(
                    doctor.getD_consultationFee()
            );
        }


        // =====================================================
        // PATIENT INFORMATION
        // =====================================================

        Patient patient =
                appointment.getPatient();

        if (patient != null) {

            response.setPatientId(
                    patient.getP_id()
            );

            if (patient.getUser() != null) {

                response.setPatientName(
                        patient.getUser().getName()
                );

                response.setPatientPhone(
                        patient.getUser().getPhone()
                );
            }
        }


        // =====================================================
        // SLOT INFORMATION
        // =====================================================

        if (appointment.getSlot() != null) {

            response.setSlotId(
                    appointment
                            .getSlot()
                            .getId()
            );
        }


        return response;
    }
}