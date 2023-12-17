package dev.patika.vetclinic.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "animals")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Animal { // This entity class includes the information of the animals that the customers have.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "animal_name")
    private String name;

    @Column(name = "animal_species")
    private String species;

    @Column(name = "animal_breed")
    private String breed;

    @Enumerated(EnumType.STRING)
    @Column(name = "animal_gender")
    private GENDER gender;

    @Column(name = "animal_color")
    private String color;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    public enum GENDER {
        MALE,
        FEMALE
    }

    @ManyToOne(fetch = FetchType.EAGER) // Many animals can belong to one customer.
    @JoinColumn(name = "animal_customer_id", referencedColumnName = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.EAGER) // Many animals can have one vaccine.
    @JoinColumn(name = "animal_vaccine_id", referencedColumnName = "vaccine_id", nullable = true)
    private Vaccine vaccine;

    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL, fetch = FetchType.LAZY) // One animal can have many appointments.
    private List<Appointment> appointments;
}
