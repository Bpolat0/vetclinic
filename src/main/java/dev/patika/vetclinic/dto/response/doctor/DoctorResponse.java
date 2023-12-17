package dev.patika.vetclinic.dto.response.doctor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorResponse { // This data transfer object includes the information of the doctors.
    private Long id;
    private String name;
}