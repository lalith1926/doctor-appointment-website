package com.doctor.website.DTO;

public class AdminDashboardResponse {

    private long doctorCount;
    private long patientCount;
    private long appointmentCount;

    public AdminDashboardResponse(
            long doctorCount,
            long patientCount,
            long appointmentCount) {

        this.doctorCount = doctorCount;
        this.patientCount = patientCount;
        this.appointmentCount = appointmentCount;
    }

    public long getDoctorCount() {
        return doctorCount;
    }

    public long getPatientCount() {
        return patientCount;
    }

    public long getAppointmentCount() {
        return appointmentCount;
    }
}