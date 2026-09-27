package com.doctor.website.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.doctor.website.DTO.DoctorResponse;
import com.doctor.website.entity.Doctor;
import com.doctor.website.entity.User;
import com.doctor.website.enums.Role;
import com.doctor.website.repository.DoctorRepository;

@Service
public class DoctorServiceImpl
        implements DoctorService {

    private final DoctorRepository doctorRepository;


    public DoctorServiceImpl(
            DoctorRepository doctorRepository) {

        this.doctorRepository =
                doctorRepository;
    }


    // =========================================================
    // GET ACTIVE DOCTORS
    // =========================================================

    @Override
    public List<DoctorResponse> getActiveDoctors() {

        return doctorRepository
                .findByUser_RoleAndUser_Active(
                        Role.DOCTOR,
                        true
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    // =========================================================
    // MAP DOCTOR
    // =========================================================

    private DoctorResponse mapToResponse(
            Doctor doctor) {

        User user =
                doctor.getUser();


        return new DoctorResponse(

                doctor.getD_id(),

                doctor.getD_name(),

                user.getEmail(),

                user.getPhone(),

                doctor.getD_specialization(),

                doctor.getD_experience(),

                doctor.getD_qualification(),

                doctor.getD_hospital(),

                doctor.getD_consultationFee(),

                doctor.getD_availableFrom(),

                doctor.getD_availableTo()
        );
    }
}