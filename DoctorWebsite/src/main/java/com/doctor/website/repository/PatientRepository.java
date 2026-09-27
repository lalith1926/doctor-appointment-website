package com.doctor.website.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.doctor.website.entity.Patient;

@Repository
public interface PatientRepository
        extends JpaRepository<Patient, Integer> {

    Optional<Patient> findByUser_Email(String email);
}