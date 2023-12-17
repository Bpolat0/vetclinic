package dev.patika.vetclinic.dao;

import dev.patika.vetclinic.entities.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AnimalRepo extends JpaRepository<Animal, Long> {

    @Query("SELECT a FROM Animal a WHERE a.customer.id = :customerId")
    List<Animal> findAllByCustomerId(@Param("customerId") Long customerId); // This method finds the animals by customer id.

    @Query("SELECT a FROM Animal a WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Animal> findByNameContainingIgnoreCase(@Param("name") String name);

    List<Animal> findByVaccineId(Long vaccineId); // This method finds the animals by vaccine id.

    List<Animal> findAllByVaccineProtectionStartDateGreaterThanEqualAndVaccineProtectionEndDateLessThanEqual(LocalDate startDate, LocalDate endDate);

}
