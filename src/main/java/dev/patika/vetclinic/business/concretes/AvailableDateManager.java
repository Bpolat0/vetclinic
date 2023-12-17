package dev.patika.vetclinic.business.concretes;

import dev.patika.vetclinic.business.abstracts.IAvailableDateService;
import dev.patika.vetclinic.core.exception.NotFoundException;
import dev.patika.vetclinic.core.result.ResultData;
import dev.patika.vetclinic.core.utilies.Msg;
import dev.patika.vetclinic.core.utilies.ResultHelper;
import dev.patika.vetclinic.dao.AnimalRepo;
import dev.patika.vetclinic.dao.AvailableDateRepo;
import dev.patika.vetclinic.dao.DoctorRepo;
import dev.patika.vetclinic.entities.AvailableDate;
import dev.patika.vetclinic.entities.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AvailableDateManager implements IAvailableDateService { // This class implements the IAvailableDateService interface.
    private final AvailableDateRepo availableDateRepo;
    private final AnimalRepo animalRepo;
    private final DoctorRepo doctorRepo;

    public AvailableDateManager(AvailableDateRepo availableDateRepo, AnimalRepo animalRepo, DoctorRepo doctorRepo) {
        this.availableDateRepo = availableDateRepo;
        this.animalRepo = animalRepo;
        this.doctorRepo = doctorRepo;
    }

    @Override
    public ResultData<AvailableDate> save(AvailableDate availableDate) {
        // Check if the doctor with the given id exists
        Doctor doctor = doctorRepo.findById(availableDate.getDoctor().getId())
                .orElseThrow(() -> new NotFoundException("Doctor with id " + availableDate.getDoctor().getId() + " not found"));

        // Check if an available date for the same doctor and date already exists
        List<AvailableDate> existingDates = availableDateRepo.findByDoctorIdAndAvailableDate(doctor.getId(), availableDate.getAvailableDate());
        if (!existingDates.isEmpty()) {
            return ResultHelper.doctorNotAvailable();
        }

        // Save the available date
        return ResultHelper.created(availableDateRepo.save(availableDate));
    }

    @Override
    public AvailableDate get(Long id) {
        // This method gets the available date by id.
        return this.availableDateRepo.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
    }

    @Override
    public boolean delete(Long id) {
        // This method deletes the available date by id.
        AvailableDate availableDate = this.get(id);
        this.availableDateRepo.delete(availableDate);
        return true;
    }

    @Override
    public AvailableDate update(AvailableDate availableDate) {
        // Check if the available date with the given id exists
        AvailableDate existingAvailableDate = this.availableDateRepo.findById(availableDate.getId())
                .orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));

        // Update the details of the existing available date
        existingAvailableDate.setAvailableDate(availableDate.getAvailableDate());
        existingAvailableDate.setDoctor(availableDate.getDoctor());

        // Save the updated available date in the database
        AvailableDate updatedAvailableDate = this.availableDateRepo.save(existingAvailableDate);

        // Return the updated available date
        return updatedAvailableDate;
    }

    @Override
    public Page<AvailableDate> cursor(int page, int size) {
        // This method returns the available dates with pagination.
        Pageable pageable = PageRequest.of(page, size);
        return this.availableDateRepo.findAll(pageable);
    }

    @Override
    public List<AvailableDate> findByDoctorIdAndAvailableDate(Long doctorId, LocalDate date) {
        // This method returns the available dates by doctor id and date.
        return this.availableDateRepo.findByDoctorIdAndAvailableDate(doctorId, date);
    }

    @Override
    public boolean isDoctorAvailable(Long doctorId, LocalDate date) {
        List<AvailableDate> availableDates = availableDateRepo.findByDoctorIdAndAvailableDate(doctorId, date);
        return !availableDates.isEmpty();
    }
}