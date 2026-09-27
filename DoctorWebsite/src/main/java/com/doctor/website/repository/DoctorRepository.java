package com.doctor.website.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.doctor.website.entity.Doctor;
import com.doctor.website.enums.Role;

@Repository
public interface DoctorRepository
        extends JpaRepository<Doctor, Integer> {

    Optional<Doctor> findByUser_Email(String email);

    List<Doctor> findByUser_RoleAndUser_Active(
            Role role,
            boolean active
    );
}