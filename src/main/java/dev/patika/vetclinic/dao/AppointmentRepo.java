package dev.patika.vetclinic.dao;

import dev.patika.vetclinic.entities.Animal;
import dev.patika.vetclinic.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment, Long> { // This interface contains CRUD operations for Appointment objects.

    List<Appointment> findByDoctorIdAndAppointmentDate(Long doctorId, LocalDateTime dateTime); // This method finds the appointments by doctor id and appointment date.

    List<Appointment> findByDoctorIdAndAppointmentDateBetween(Long doctorId, LocalDateTime startDateTime, LocalDateTime endDateTime); // This method finds the appointments by doctor id and appointment date between start date time and end date time.

    List<Appointment> findByAppointmentDateBetweenAndAnimal(LocalDateTime startDateTime, LocalDateTime endDateTime, Animal animal); // This method finds the appointments by appointment date between start date time and end date time and animal.

    List<Appointment> findByAnimalIdAndAppointmentDate(Long animalId, LocalDateTime dateTime);

    List<Appointment> findByAnimalId(Long animalId);
}

