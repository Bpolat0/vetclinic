package dev.patika.vetclinic.api;

import dev.patika.vetclinic.business.abstracts.IDoctorService;
import dev.patika.vetclinic.core.config.modelMapper.IModelMapperService;
import dev.patika.vetclinic.core.result.Result;
import dev.patika.vetclinic.core.result.ResultData;
import dev.patika.vetclinic.core.utilies.ResultHelper;
import dev.patika.vetclinic.dto.request.doctor.DoctorSaveRequest;
import dev.patika.vetclinic.dto.request.doctor.DoctorUpdateRequest;
import dev.patika.vetclinic.dto.response.CursorResponse;
import dev.patika.vetclinic.dto.response.doctor.DoctorResponse;
import dev.patika.vetclinic.entities.Doctor;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/doctors")
public class DoctorController { // This class contains methods that control the Doctor endpoints.
    private final IDoctorService doctorService;
    private final IModelMapperService modelMapper;

    public DoctorController(IDoctorService doctorService, IModelMapperService modelMapper) {
        this.doctorService = doctorService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<DoctorResponse> save(@Valid @RequestBody DoctorSaveRequest doctorSaveRequest) {
        // This method saves the doctor.
        Doctor saveDoctor = this.modelMapper.forRequest().map(doctorSaveRequest, Doctor.class);
        ResultData<Doctor> result = this.doctorService.save(saveDoctor);

        // Check if a doctor with the same email or phone already exists
        if (!result.isSuccess()) {
            return new ResultData<>(false, result.getMessage(), "400", null);
        }

        return ResultHelper.created(this.modelMapper.forResponse().map(result.getData(), DoctorResponse.class));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<DoctorResponse> get(@PathVariable("id") Long id) {
        // This method gets the doctor by id.
        Doctor doctor = this.doctorService.get(id);
        return ResultHelper.success(this.modelMapper.forResponse().map(doctor, DoctorResponse.class));
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<DoctorResponse>> cursor(
            // This method returns the doctors with pagination.
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size)
    {
        Page<Doctor> doctors = this.doctorService.cursor(page, size);
        Page<DoctorResponse> doctorResponsePage = doctors
                .map(doctor -> this.modelMapper.forResponse().map(doctor, DoctorResponse.class));

        return ResultHelper.cursor(doctorResponsePage);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<DoctorResponse> update(@Valid @RequestBody DoctorUpdateRequest doctorUpdateRequest) {
        Doctor updateDoctor = this.modelMapper.forRequest().map(doctorUpdateRequest, Doctor.class);
        ResultData<Doctor> result = this.doctorService.update(updateDoctor);

        if (!result.isSuccess()) {
            return new ResultData<>(false, result.getMessage(), "400", null);
        }

        return ResultHelper.success(this.modelMapper.forResponse().map(result.getData(), DoctorResponse.class));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") Long id) {
        // This method deletes the doctor by id.
        this.doctorService.delete(id);
        return ResultHelper.ok();
    }
}