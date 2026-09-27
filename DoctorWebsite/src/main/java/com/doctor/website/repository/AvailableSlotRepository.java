package com.doctor.website.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.doctor.website.entity.AvailableSlot;

import jakarta.persistence.LockModeType;

@Repository
public interface AvailableSlotRepository
        extends JpaRepository<AvailableSlot, Integer> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
        SELECT s
        FROM AvailableSlot s
        WHERE s.id = :slotId
    """)
    Optional<AvailableSlot> findByIdForUpdate(
            @Param("slotId") int slotId
    );

    @Query("""
        SELECT s
        FROM AvailableSlot s
        WHERE s.doctor.d_id = :doctorId
        AND s.date = :date
    """)
    List<AvailableSlot> findSlotsByDoctorAndDate(
            @Param("doctorId") int doctorId,
            @Param("date") LocalDate date
    );
}