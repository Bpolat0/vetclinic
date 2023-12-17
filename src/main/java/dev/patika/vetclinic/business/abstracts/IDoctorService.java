package dev.patika.vetclinic.business.abstracts;

import dev.patika.vetclinic.core.result.ResultData;
import dev.patika.vetclinic.entities.Doctor;
import org.springframework.data.domain.Page;

public interface IDoctorService { // This interface includes the methods that the DoctorManager class will implement.

    ResultData<Doctor> save(Doctor doctor);

    Doctor get(Long id);

    boolean delete(Long id);

    ResultData<Doctor> update(Doctor doctor);

    Page<Doctor> cursor(int page, int size);

}
