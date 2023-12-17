package dev.patika.vetclinic.business.concretes;

import dev.patika.vetclinic.business.abstracts.IDoctorService;
import dev.patika.vetclinic.core.exception.NotFoundException;
import dev.patika.vetclinic.core.result.ResultData;
import dev.patika.vetclinic.core.utilies.Msg;
import dev.patika.vetclinic.core.utilies.ResultHelper;
import dev.patika.vetclinic.dao.DoctorRepo;
import dev.patika.vetclinic.entities.Doctor;
import dev.patika.vetclinic.entities.Vaccine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DoctorManager implements IDoctorService { // This class implements the IDoctorService interface.

    private final DoctorRepo doctorRepo;

    public DoctorManager(DoctorRepo doctorRepo) {
        // This method injects the DoctorRepo object.
        this.doctorRepo = doctorRepo;
    }

    @Override
    public ResultData<Doctor> save(Doctor doctor) {
        // Check if a doctor with the same email or phone already exists
        if (doctorRepo.existsByEmail(doctor.getEmail())) {
            return ResultHelper.EmailExists();
        }
        if (doctorRepo.existsByPhone(doctor.getPhone())) {
            return ResultHelper.PhoneExists();
        }

        // Save the new doctor and return it
        Doctor savedDoctor = doctorRepo.save(doctor);
        return ResultHelper.created(savedDoctor);
    }

    @Override
    public Doctor get(Long id) {
        // This method gets the doctor by id.
        return this.doctorRepo.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
    }

    @Override
    public boolean delete(Long id) {
        // This method deletes the doctor by id.
        Doctor doctor = this.get(id);
        this.doctorRepo.delete(doctor);
        return true;
    }

    @Override
    public ResultData<Doctor> update(Doctor doctor) {
        // Check if a doctor with the same email or phone already exists
        if (doctorRepo.existsByEmail(doctor.getEmail()) && !doctorRepo.getOne(doctor.getId()).getEmail().equals(doctor.getEmail())) {
            return ResultHelper.EmailExists();
        }
        if (doctorRepo.existsByPhone(doctor.getPhone()) && !doctorRepo.getOne(doctor.getId()).getPhone().equals(doctor.getPhone())) {
            return ResultHelper.PhoneExists();
        }

        // Update the doctor
        Doctor existingDoctor = this.doctorRepo.findById(doctor.getId())
                .orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
        existingDoctor.setName(doctor.getName());
        existingDoctor.setPhone(doctor.getPhone());
        existingDoctor.setEmail(doctor.getEmail());
        existingDoctor.setAddress(doctor.getAddress());
        existingDoctor.setCity(doctor.getCity());
        return ResultHelper.success(this.doctorRepo.save(existingDoctor));
    }

    @Override
    public Page<Doctor> cursor(int page, int size) {
        // This method returns the doctors with pagination.
        Pageable pageable = PageRequest.of(page, size);
        return this.doctorRepo.findAll(pageable);
    }

}
