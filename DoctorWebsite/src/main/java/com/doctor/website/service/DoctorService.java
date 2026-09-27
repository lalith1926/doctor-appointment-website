package com.doctor.website.service;

import java.util.List;

import com.doctor.website.DTO.DoctorResponse;

public interface DoctorService {

    List<DoctorResponse> getActiveDoctors();

}