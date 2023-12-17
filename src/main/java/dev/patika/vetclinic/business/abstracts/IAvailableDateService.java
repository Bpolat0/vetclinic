package dev.patika.vetclinic.business.abstracts;

import dev.patika.vetclinic.entities.AvailableDate;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface IAvailableDateService { // This interface includes the methods that the AvailableDateManager class will implement.

    AvailableDate save(AvailableDate availableDate);

    AvailableDate get(Long id);

    boolean delete(Long id);

    AvailableDate update(AvailableDate availableDate);

    Page<AvailableDate> cursor(int page, int size);

    List<AvailableDate> findByDoctorIdAndAvailableDate(Long doctorId, LocalDate date);
}