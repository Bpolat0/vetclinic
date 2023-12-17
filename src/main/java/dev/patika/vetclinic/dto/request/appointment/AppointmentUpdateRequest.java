package dev.patika.vetclinic.dto.request.appointment;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentUpdateRequest { // This data transfer object includes the information of the appointments that the animals have.

    @Min(value = 1, message = "Id 1'den küçük olamaz.")
    private Long id;

    @NotNull(message = "Appointment date cannot be null.")
    private LocalDateTime appointmentDate;

    @NotNull(message = "Animal ID cannot be null.")
    @Min(value = 1, message = "Id 1'den küçük olamaz.")
    private Long animalId;

    @NotNull(message = "Doctor ID cannot be null.")
    @Min(value = 1, message = "Id 1'den küçük olamaz.")
    private Long doctorId;
}