package dev.patika.vetclinic.dto.response.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse { // This data transfer object includes the information of the customers.
    private Long id;
    private String name;
}
