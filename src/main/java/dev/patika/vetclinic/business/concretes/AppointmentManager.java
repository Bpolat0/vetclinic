package dev.patika.vetclinic.business.concretes;

import dev.patika.vetclinic.business.abstracts.IAppointmentService;
import dev.patika.vetclinic.core.exception.NotFoundException;
import dev.patika.vetclinic.core.result.ResultData;
import dev.patika.vetclinic.core.utilies.Msg;
import dev.patika.vetclinic.core.utilies.ResultHelper;
import dev.patika.vetclinic.dao.AnimalRepo;
import dev.patika.vetclinic.dao.AppointmentRepo;
import dev.patika.vetclinic.dao.AvailableDateRepo;
import dev.patika.vetclinic.entities.Animal;
import dev.patika.vetclinic.entities.Appointment;
import dev.patika.vetclinic.entities.AvailableDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentManager implements IAppointmentService { // This class implements the IAppointmentService interface.
    private final AppointmentRepo appointmentRepo;
    private final AvailableDateRepo availableDateRepo;
    private final AnimalRepo animalRepo;

    public AppointmentManager(AppointmentRepo appointmentRepo, AvailableDateRepo availableDateRepo, AnimalRepo animalRepo) {
        this.appointmentRepo = appointmentRepo;
        this.availableDateRepo = availableDateRepo;
        this.animalRepo = animalRepo;
    }

    @Override
    public ResultData<Appointment> createAppointment(Appointment appointment, LocalDateTime dateTime) {
        // Check if the animal exists
        Animal animal = animalRepo.findById(appointment.getAnimal().getId())
                .orElseThrow(() -> new NotFoundException("Animal with id " + appointment.getAnimal().getId() + " not found"));
        if (animal == null) {
            return ResultHelper.animalNotFoundError();
        }

        // Check if the doctor is available at the given date
        if (!availableDateRepo.isDoctorAvailable(appointment.getDoctor().getId(), dateTime.toLocalDate())) {
            return ResultHelper.doctorNotAvailable();
        }

        // Check if the doctor already has an appointment at the given date and time
        List<Appointment> existingAppointments = appointmentRepo.findByDoctorIdAndAppointmentDate(appointment.getDoctor().getId(), dateTime);
        if (!existingAppointments.isEmpty()) {
            return ResultHelper.appointmentAlreadyExists();
        }

        // Check if the animal already has an appointment at any time
        List<Appointment> existingAnimalAppointments = appointmentRepo.findByAnimalId(appointment.getAnimal().getId());
        if (!existingAnimalAppointments.isEmpty()) {
            return ResultHelper.appointmentAlreadyExists();
        }

        // Set the appointment date
        appointment.setAppointmentDate(dateTime);

        // Save the new appointment and return it
        Appointment savedAppointment = appointmentRepo.save(appointment);
        return ResultHelper.created(savedAppointment);
    }

    @Override
    public List<Appointment> findByDoctorIdAndAppointmentDateBetween(Long doctorId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        // This method returns the appointments of the doctor between the given dates.
        return this.appointmentRepo.findByDoctorIdAndAppointmentDateBetween(doctorId, startDateTime, endDateTime);
    }

    @Override
    public List<Appointment> findByAppointmentDateBetweenAndAnimal(LocalDateTime startDateTime, LocalDateTime endDateTime, Animal animal) {
        // This method returns the appointments of the animal between the given dates.
        return this.appointmentRepo.findByAppointmentDateBetweenAndAnimal(startDateTime, endDateTime, animal);
    }

    @Override
    public Appointment get(Long id) {
        // This method gets the appointment by id.
        return this.appointmentRepo.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
    }

    @Override
    public boolean delete(Long id) {
        // This method deletes the appointment by id.
        Appointment appointment = this.get(id);
        this.appointmentRepo.delete(appointment);
        return true;
    }

    @Override
    public ResultData<Appointment> update(Long id, Appointment appointment) {
        // Check if the appointment with the given id exists
        Appointment existingAppointment = this.appointmentRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));

        // Check if the animal exists
        Animal animal = animalRepo.findById(appointment.getAnimal().getId())
                .orElse(null);
        if (animal == null) {
            return ResultHelper.animalNotFoundError();
        }

        // Update the details of the existing appointment
        existingAppointment.setAppointmentDate(appointment.getAppointmentDate());
        existingAppointment.setAnimal(appointment.getAnimal());
        existingAppointment.setDoctor(appointment.getDoctor());

        // Save the updated appointment in the database
        Appointment updatedAppointment = this.appointmentRepo.save(existingAppointment);

        // Return the updated appointment
        return ResultHelper.success(updatedAppointment);
    }

    @Override
    public Page<Appointment> cursor(int page, int size) {
        // This method returns the appointments with pagination.
        Pageable pageable = PageRequest.of(page, size);
        return this.appointmentRepo.findAll(pageable);
    }


}