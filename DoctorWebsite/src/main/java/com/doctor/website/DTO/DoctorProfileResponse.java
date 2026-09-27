package com.doctor.website.DTO;

public class DoctorProfileResponse {

    private String token;

    private int id;

    private int doctorId;

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


    public DoctorProfileResponse(
            String token,
            int id,
            int doctorId,
            String name,
            String email,
            String phone,
            String specialization,
            String experience,
            String qualification,
            String hospital,
            int consultationFee,
            String availableFrom,
            String availableTo) {

        this.token = token;
        this.id = id;
        this.doctorId = doctorId;
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
    }


    public String getToken() {
        return token;
    }

    public int getId() {
        return id;
    }

    public int getDoctorId() {
        return doctorId;
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
}