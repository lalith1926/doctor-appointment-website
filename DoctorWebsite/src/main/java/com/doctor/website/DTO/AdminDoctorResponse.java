package com.doctor.website.DTO;

public class AdminDoctorResponse {

    private int doctorId;

    private int userId;

    private String name;

    private String email;

    private String phone;

    private String specialization;

    private String experience;

    private String qualification;

    private String hospital;

    private int consultationFee;

    private String availableFrom;

    private String availableTo;

    private boolean active;

    private long patientCount;


    public AdminDoctorResponse(

            int doctorId,

            int userId,

            String name,

            String email,

            String phone,

            String specialization,

            String experience,

            String qualification,

            String hospital,

            int consultationFee,

            String availableFrom,

            String availableTo,

            boolean active,

            long patientCount) {

        this.doctorId = doctorId;

        this.userId = userId;

        this.name = name;

        this.email = email;

        this.phone = phone;

        this.specialization = specialization;

        this.experience = experience;

        this.qualification = qualification;

        this.hospital = hospital;

        this.consultationFee = consultationFee;

        this.availableFrom = availableFrom;

        this.availableTo = availableTo;

        this.active = active;

        this.patientCount = patientCount;
    }


    public int getDoctorId() {
        return doctorId;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getExperience() {
        return experience;
    }

    public String getQualification() {
        return qualification;
    }

    public String getHospital() {
        return hospital;
    }

    public int getConsultationFee() {
        return consultationFee;
    }

    public String getAvailableFrom() {
        return availableFrom;
    }

    public String getAvailableTo() {
        return availableTo;
    }

    public boolean isActive() {
        return active;
    }

    public long getPatientCount() {
        return patientCount;
    }
}