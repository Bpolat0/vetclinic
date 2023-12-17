package dev.patika.vetclinic.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CursorResponse <T>{ // This class is used to return the data in a paginated way.
    private int page;
    private int size;
    private Long totalElements;
    private List<T> items;
}
