package dev.patika.vetclinic.dto.request.animal;

import dev.patika.vetclinic.entities.Animal;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalUpdateRequest { // This data transfer object includes the information of the animals that the customers have.
    @Min(value = 1, message = "Hayvan ID'si 1'den büyük olmalıdır.")
    private Long id;

    @NotNull(message = "İsim boş olamaz.")
    @Size(min = 2, max = 50, message = "İsim 2 ile 30 karakter arasında olmalıdır.")
    private String name;

    @NotNull(message = "Tür boş olamaz.")
    @Size(min = 2, max = 50, message = "Tür 2 ile 30 karakter arasında olmalıdır.")
    private String species;

    @NotNull(message = "Irk boş olamaz.")
    @Size(min = 2, max = 50, message = "Irk 2 ile 30 karakter arasında olmalıdır.")
    private String breed;

    @NotNull(message = "Cinsiyet boş olamaz.")
    private Animal.GENDER gender;

    @NotNull(message = "Renk boş olamaz.")
    @Size(min = 2, max = 50, message = "Renk 2 ile 30 karakter arasında olmalıdır.")
    private String color;

    @NotNull(message = "Doğum tarihi boş olamaz.")
    private LocalDate dateOfBirth;

    @NotNull(message = "Müşteri ID boş olamaz.")
    @Min(value = 1, message = "Müşteri ID'si 1'den büyük olmalıdır.")
    private Long customerId;
}