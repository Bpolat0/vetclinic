package dev.patika.vetclinic.dto.response.availabledate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailableDateResponse { // This data transfer object includes the information of the available dates that the doctors have.
    private Long id;
    private LocalDate availableDate;
    private Long doctorId;
}