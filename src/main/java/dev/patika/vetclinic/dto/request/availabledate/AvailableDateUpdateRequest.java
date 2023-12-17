package dev.patika.vetclinic.dto.request.availabledate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailableDateUpdateRequest { // This data transfer object includes the information of the available dates that the doctors have.

    @Min(value = 1, message = "Id 1'den küçük olamaz.")
    private Long id;

    @NotNull(message = "Available date cannot be null.")
    private LocalDate availableDate;

    @NotNull(message = "Doctor ID cannot be null.")
    @Min(value = 1, message = "Id 1'den küçük olamaz.")
    private Long doctorId;
}