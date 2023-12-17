package dev.patika.vetclinic.business.concretes;

import dev.patika.vetclinic.business.abstracts.IAppointmentService;
import dev.patika.vetclinic.core.exception.NotFoundException;
import dev.patika.vetclinic.core.result.ResultData;
import dev.patika.vetclinic.core.utilies.Msg;
import dev.patika.vetclinic.core.utilies.ResultHelper;
import dev.patika.vetclinic.dao.AppointmentRepo;
import dev.patika.vetclinic.entities.Animal;
import dev.patika.vetclinic.entities.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentManager implements IAppointmentService { // This class implements the IAppointmentService interface.
    private final AppointmentRepo appointmentRepo;

    public AppointmentManager(AppointmentRepo appointmentRepo) {
        this.appointmentRepo = appointmentRepo;
    }

    @Override
    public ResultData<Appointment> createAppointment(Appointment appointment, LocalDateTime dateTime) {
        // Check if the doctor is available at the given date and time
        List<Appointment> existingAppointments = appointmentRepo.findByDoctorIdAndAppointmentDate(appointment.getDoctor().getId(), dateTime);

        // If there is already an appointment at the given date and time, throw an exception
        if (!existingAppointments.isEmpty()) {
            return ResultHelper.doctorNotAvailable();
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