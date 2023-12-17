package dev.patika.vetclinic.dto.request.vaccine;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VaccinationRequest {
    @NotNull
    private Long animalId;
    @NotNull
    private Long vaccineId;
}