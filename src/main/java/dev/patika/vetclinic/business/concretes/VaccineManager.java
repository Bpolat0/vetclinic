package dev.patika.vetclinic.business.concretes;

import dev.patika.vetclinic.business.abstracts.IVaccineService;
import dev.patika.vetclinic.core.exception.NotFoundException;
import dev.patika.vetclinic.core.result.ResultData;
import dev.patika.vetclinic.core.utilies.Msg;
import dev.patika.vetclinic.core.utilies.ResultHelper;
import dev.patika.vetclinic.dao.VaccineRepo;
import dev.patika.vetclinic.entities.Vaccine;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class VaccineManager implements IVaccineService { // This class implements the IVaccineService interface.
    private final VaccineRepo vaccineRepo;

    public VaccineManager(VaccineRepo vaccineRepo) {
        this.vaccineRepo = vaccineRepo;
    }

    @Override
    public ResultData<Vaccine> save(Vaccine vaccine) {
        // Check if a vaccine with the same name or code already exists
        if (vaccineRepo.existsByName(vaccine.getName())) {
            return ResultHelper.vaccineNameAndCodeExists();
        }
        if (vaccineRepo.existsByCode(vaccine.getCode())) {
            return ResultHelper.vaccineNameAndCodeExists();
        }

        // Save the new vaccine and return it
        Vaccine savedVaccine = vaccineRepo.save(vaccine);
        return ResultHelper.created(savedVaccine);
    }

    @Override
    public Vaccine get(Long id) {
        // This method gets the vaccine by id.
        return this.vaccineRepo.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
    }

    @Override
    public boolean delete(Long id) {
        // This method deletes the vaccine by id.
        Vaccine vaccine = this.get(id);
        this.vaccineRepo.delete(vaccine);
        return true;
    }

    @Override
    public ResultData<Vaccine> update(Vaccine vaccine) {
        // Check if the vaccine exists
        Vaccine existingVaccine = this.vaccineRepo.findById(vaccine.getId())
                .orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));

        // Check if a vaccine with the same name or code already exists
        if (vaccineRepo.existsByName(vaccine.getName())) {
            return ResultHelper.vaccineNameAndCodeExists();
        }
        if (vaccineRepo.existsByCode(vaccine.getCode())) {
            return ResultHelper.vaccineNameAndCodeExists();
        }

        // Update the vaccine
        existingVaccine.setName(vaccine.getName());
        existingVaccine.setCode(vaccine.getCode());
        existingVaccine.setProtectionStartDate(vaccine.getProtectionStartDate());
        existingVaccine.setProtectionEndDate(vaccine.getProtectionEndDate());

        return ResultHelper.success(this.vaccineRepo.save(existingVaccine));
    }

    @Override
    public Page<Vaccine> cursor(int page, int size) {
        // This method returns the vaccines by page.
        Pageable pageable = PageRequest.of(page, size);
        return this.vaccineRepo.findAll(pageable);
    }

    @Override
    public List<Vaccine> findByAnimalId(Long animalId) {
        // This method finds the vaccines by animal id.
        return this.vaccineRepo.findByAnimalsId(animalId);
    }

    @Override
    public List<Vaccine> findByProtectionEndDateBetween(LocalDate startDate, LocalDate endDate) {
        // This method finds the vaccines by protection end date between start date and end date.
        return this.vaccineRepo.findByProtectionEndDateBetween(startDate, endDate);
    }


}
