package dev.patika.vetclinic.business.abstracts;

import dev.patika.vetclinic.entities.Animal;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface IAnimalService { // This interface includes the methods that the AnimalManager class will implement.
    Animal save(Animal animal);

    Animal get(Long id);

    boolean delete(Long id);

    Animal update(Long id, Animal animal);

    Page<Animal> cursor(int page, int size);

    List<Animal> findByCustomerId(Long customerId);

    List<Animal> findByVaccineId(Long vaccineId);

    List<Animal> findByNameContainingIgnoreCase(String name);

    List<Animal> findAllByVaccineProtectionStartDateGreaterThanEqualAndVaccineProtectionEndDateLessThanEqual(LocalDate startDate, LocalDate endDate);
}
