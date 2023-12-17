package dev.patika.vetclinic.dto.request.vaccine;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VaccineUpdateRequest { // This data transfer object includes the information of the vaccines that the animals have.
    @Min(value = 1, message = "Aşı id'si 1'den büyük olmalıdır.")
    private Long id;

    @NotNull(message = "Aşı adı boş veya null olamaz.")
    private String name;

    @NotNull(message = "Aşı kodu boş veya null olamaz.")
    private String code;

    @NotNull(message = "Aşı koruma başlangıç tarihi boş olamaz.")
    private LocalDate protectionStartDate;

    @NotNull(message = "Aşı koruma bitiş tarihi boş olamaz.")
    private LocalDate protectionEndDate;
}