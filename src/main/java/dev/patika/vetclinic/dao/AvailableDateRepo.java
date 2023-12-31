package dev.patika.vetclinic.dao;

import dev.patika.vetclinic.entities.AvailableDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AvailableDateRepo extends JpaRepository<AvailableDate, Long> { // This interface contains CRUD operations for AvailableDate objects.
    List<AvailableDate> findByDoctorIdAndAvailableDate(Long doctorId, LocalDate date);

    @Query("SELECT CASE WHEN COUNT(ad) > 0 THEN true ELSE false END FROM AvailableDate ad WHERE ad.doctor.id = :doctorId AND ad.availableDate = :date")
    boolean isDoctorAvailable(@Param("doctorId") Long doctorId, @Param("date") LocalDate date);
}
