package com.doctor.website.DTO;

public class PatientProfileResponse {

    private String token;

    private int id;

    private int patientId;

    private String name;

    private String email;

    private String phone;

    private int age;

    private String gender;

    private String address;

    private String bloodGroup;


    public PatientProfileResponse(
            String token,
            int id,
            int patientId,
            String name,
            String email,
            String phone,
            int age,
            String gender,
            String address,
            String bloodGroup) {

        this.token = token;
        this.id = id;
        this.patientId = patientId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.bloodGroup = bloodGroup;
    }


    public String getToken() {
        return token;
    }

    public int getId() {
        return id;
    }

    public int getPatientId() {
        return patientId;
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

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }
}