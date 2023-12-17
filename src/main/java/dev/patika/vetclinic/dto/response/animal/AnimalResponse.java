package dev.patika.vetclinic.dto.response.animal;

import dev.patika.vetclinic.entities.Animal;
import dev.patika.vetclinic.entities.Customer;
import dev.patika.vetclinic.entities.Vaccine;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalResponse { // This data transfer object includes the information of the animals that the customers have.
    private Long id;
    private String name;
    private String species;
    private String breed;
    private Animal.GENDER gender;
    private String color;
    private LocalDate dateOfBirth;
    private Long customerId;
}

