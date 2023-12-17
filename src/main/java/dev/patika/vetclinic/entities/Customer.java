package dev.patika.vetclinic.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "customers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer { // This entity class includes the information of the customers who have animals.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id", columnDefinition = "serial")
    private Long id;


    @Column(name = "customer_name")
    private String name;


    @Column(name = "customer_phone", unique = true)
    private String phone;


    @Column(name = "customer_email", unique = true)
    private String email;


    @Column(name = "customer_address")
    private String address;

    @Column(name = "customer_city")
    private String city;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY) // One customer can have many animals.
    private List<Animal> animals;

}
