package dev.patika.vetclinic.dto.request.doctor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorSaveRequest { // This data transfer object includes the information of the doctors.
    @NotNull(message = "Doktor adı boş olamaz.")
    private String name;

    @NotNull(message = "Telefon numarası boş olamaz.")
    private String phone;

    @Email(message = "Geçerli bir email adresi giriniz.")
    @NotNull(message = "Email boş olamaz.")
    private String email;

    @NotNull(message = "Adres boş olamaz.")
    private String address;

    @NotNull(message = "Şehir boş olamaz.")
    private String city;
}