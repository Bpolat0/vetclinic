package dev.patika.vetclinic.business.abstracts;

import dev.patika.vetclinic.core.result.ResultData;
import dev.patika.vetclinic.entities.Animal;
import dev.patika.vetclinic.entities.Appointment;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public interface IAppointmentService { // This interface includes the methods that the AppointmentManager class will implement.

    ResultData<Appointment> createAppointment(Appointment appointment, LocalDateTime dateTime);

    List<Appointment> findByDoctorIdAndAppointmentDateBetween(Long doctorId, LocalDateTime startDateTime, LocalDateTime endDateTime);

    List<Appointment> findByAppointmentDateBetweenAndAnimal(LocalDateTime startDateTime, LocalDateTime endDateTime, Animal animal);

    Appointment get(Long id);

    boolean delete(Long id);

    ResultData<Appointment> update(Long id, Appointment appointment);

    Page<Appointment> cursor(int page, int size);



}