package dev.patika.vetclinic.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "vaccines")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vaccine { // This entity class includes the information of the vaccines that the animals have.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vaccine_id", columnDefinition = "serial")
    private Long id;


    @Column(name = "vaccine_name", unique = true)
    private String name;


    @Column(name = "vaccine_code", unique = true)
    private String code;

    @Column(name = "vaccine_protection_start_date")
    private LocalDate protectionStartDate;


    @Column(name = "vaccine_protection_end_date")
    private LocalDate protectionEndDate;

    @OneToMany(mappedBy = "vaccine", fetch = FetchType.LAZY, cascade = CascadeType.ALL) // One vaccine can have many animals.
    private List<Animal> animals;

}
