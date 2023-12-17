package dev.patika.vetclinic.dao;

import dev.patika.vetclinic.entities.AvailableDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AvailableDateRepo extends JpaRepository<AvailableDate, Long> { // This interface contains CRUD operations for AvailableDate objects.
    List<AvailableDate> findByDoctorIdAndAvailableDate(Long doctorId, LocalDate date);
}
