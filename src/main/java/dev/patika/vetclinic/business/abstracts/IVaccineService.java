package dev.patika.vetclinic.business.abstracts;

import dev.patika.vetclinic.core.result.ResultData;
import dev.patika.vetclinic.entities.Vaccine;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface IVaccineService { // This interface includes the methods that the VaccineManager class will implement.

    ResultData<Vaccine> save(Vaccine vaccine);

    Vaccine get(Long id);

    boolean delete(Long id);

    ResultData<Vaccine> update(Vaccine vaccine);

    Page<Vaccine> cursor(int page, int size);

    List<Vaccine> findByAnimalId(Long animalId);

    List<Vaccine> findByProtectionEndDateBetween(LocalDate startDate, LocalDate endDate);


}
