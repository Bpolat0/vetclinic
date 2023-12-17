package dev.patika.vetclinic.dto.request.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerUpdateRequest { // This data transfer object includes the information of the customers that the animals have.

    @Min(value = 1, message = "Id 1'den küçük olamaz.")
    private Long id;

    @NotNull(message = "Müşteri adı boş olamaz.")
    private String name;

    @NotNull(message = "Müşteri numarası boş olamaz.")
    private String phone;

    @Email(message = "Geçerli bir mail adresi giriniz.")
    private String email;

    private String address;

    private String city;
}

