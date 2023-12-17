package dev.patika.vetclinic.dao;

import dev.patika.vetclinic.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepo extends JpaRepository<Doctor, Long> { // This interface contains CRUD operations for Doctor objects.
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
}
