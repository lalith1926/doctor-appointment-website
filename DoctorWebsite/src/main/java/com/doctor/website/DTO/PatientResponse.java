package com.doctor.website.DTO;

public class PatientResponse {

    private int p_id;
    private UserResponse user;
    private int p_age;
    private String p_gender;
    private String p_address;
    private String p_bloodGroup;

    public PatientResponse() {
    }

    public PatientResponse(
            int p_id,
            UserResponse user,
            int p_age,
            String p_gender,
            String p_address,
            String p_bloodGroup) {

        this.p_id = p_id;
        this.user = user;
        this.p_age = p_age;
        this.p_gender = p_gender;
        this.p_address = p_address;
        this.p_bloodGroup = p_bloodGroup;
    }

    public int getP_id() {
        return p_id;
    }

    public void setP_id(int p_id) {
        this.p_id = p_id;
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }

    public int getP_age() {
        return p_age;
    }

    public void setP_age(int p_age) {
        this.p_age = p_age;
    }

    public String getP_gender() {
        return p_gender;
    }

    public void setP_gender(String p_gender) {
        this.p_gender = p_gender;
    }

    public String getP_address() {
        return p_address;
    }

    public void setP_address(String p_address) {
        this.p_address = p_address;
    }

    public String getP_bloodGroup() {
        return p_bloodGroup;
    }

    public void setP_bloodGroup(String p_bloodGroup) {
        this.p_bloodGroup = p_bloodGroup;
    }
}