package dev.patika.vetclinic.dao;

import dev.patika.vetclinic.entities.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VaccineRepo extends JpaRepository<Vaccine, Long> { // This interface contains CRUD operations for Vaccine objects.

    List<Vaccine> findByNameAndCode(String name, String code); // This method finds the vaccines by name and code.

    List<Vaccine> findByProtectionEndDateBetween(LocalDate startDate, LocalDate endDate); // This method finds the vaccines by protection end date between start date and end date.

    List<Vaccine> findByAnimalsId(Long animalId); // This method finds the vaccines by animal id.

    boolean existsByName(String name);

    boolean existsByCode(String code);

}
