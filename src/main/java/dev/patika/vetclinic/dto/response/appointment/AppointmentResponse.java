package dev.patika.vetclinic.dto.response.appointment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponse { // This data transfer object includes the information of the appointments that the animals have.
    private Long id;
    private LocalDateTime appointmentDate;
    private Long animalId;
    private Long doctorId;
}