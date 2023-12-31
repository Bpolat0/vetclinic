package dev.patika.vetclinic.dto.request.appointment;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentSaveRequest { // This data transfer object includes the information of the appointments that the animals have.
    @NotNull(message = "Appointment date cannot be null.")
    private LocalDateTime date_time;

    @NotNull(message = "Animal ID cannot be null.")
    private Long animalId;

    @NotNull(message = "Doctor ID cannot be null.")
    private Long doctorId;
}