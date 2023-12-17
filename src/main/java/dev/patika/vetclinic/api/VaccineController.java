package dev.patika.vetclinic.api;

import dev.patika.vetclinic.business.abstracts.IAnimalService;
import dev.patika.vetclinic.business.abstracts.IVaccineService;
import dev.patika.vetclinic.core.config.modelMapper.IModelMapperService;
import dev.patika.vetclinic.core.result.Result;
import dev.patika.vetclinic.core.result.ResultData;
import dev.patika.vetclinic.core.utilies.ResultHelper;
import dev.patika.vetclinic.dto.request.vaccine.VaccinationRequest;
import dev.patika.vetclinic.dto.request.vaccine.VaccineSaveRequest;
import dev.patika.vetclinic.dto.request.vaccine.VaccineUpdateRequest;
import dev.patika.vetclinic.dto.response.CursorResponse;
import dev.patika.vetclinic.dto.response.animal.AnimalResponse;
import dev.patika.vetclinic.dto.response.vaccine.VaccineResponse;
import dev.patika.vetclinic.entities.Animal;
import dev.patika.vetclinic.entities.Vaccine;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/vaccines")
public class VaccineController { // This class contains methods that control the Vaccine endpoints.
    private final IVaccineService vaccineService;
    private final IAnimalService animalService;
    private final IModelMapperService modelMapper;

    public VaccineController(IVaccineService vaccineService, IAnimalService animalService, IModelMapperService modelMapper) {
        this.vaccineService = vaccineService;
        this.animalService = animalService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<VaccineResponse> save(@Valid @RequestBody VaccineSaveRequest vaccineSaveRequest) {
        // This method saves the vaccine.
        Vaccine saveVaccine = this.modelMapper.forRequest().map(vaccineSaveRequest, Vaccine.class);
        ResultData<Vaccine> result = this.vaccineService.save(saveVaccine);

        // Check if a vaccine with the same name or code already exists
        if (!result.isSuccess()) {
            return new ResultData<>(false, result.getMessage(), "400", null);
        }

        return ResultHelper.created(this.modelMapper.forResponse().map(result.getData(), VaccineResponse.class));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<VaccineResponse> get(@PathVariable("id") Long id) {
        // This method gets the vaccine by id.
        Vaccine vaccine = this.vaccineService.get(id);
        return ResultHelper.success(this.modelMapper.forResponse().map(vaccine, VaccineResponse.class));
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<VaccineResponse>> cursor(
            // This method returns the vaccines with pagination.
             @RequestParam(value = "page", required = false, defaultValue = "0") int page,
             @RequestParam(value = "size", required = false, defaultValue = "10") int size)
    {
        Page<Vaccine> vaccines = this.vaccineService.cursor(page, size);
        Page<VaccineResponse> vaccineResponsePage = vaccines
                .map(vaccine -> this.modelMapper.forResponse().map(vaccine, VaccineResponse.class));

        return ResultHelper.cursor(vaccineResponsePage);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<VaccineResponse> update(@Valid @RequestBody VaccineUpdateRequest vaccineUpdateRequest) {
        // This method updates the vaccine.
        Vaccine updateVaccine = this.modelMapper.forRequest().map(vaccineUpdateRequest, Vaccine.class);
        this.vaccineService.update(updateVaccine);
        return ResultHelper.success(this.modelMapper.forResponse().map(updateVaccine, VaccineResponse.class));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") Long id) {
        // This method deletes the vaccine by id.
        this.vaccineService.delete(id);
        return ResultHelper.ok();
    }

    @GetMapping("/animal/{animalId}")
    @ResponseStatus(HttpStatus.OK)
    public List<VaccineResponse> getByAnimalId(@PathVariable("animalId") Long animalId) {
        // This method returns the vaccines by animal id.
        List<Vaccine> vaccines = this.vaccineService.findByAnimalId(animalId);
        return vaccines.stream()
                .map(vaccine -> this.modelMapper.forResponse().map(vaccine, VaccineResponse.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/protection-dates")
    @ResponseStatus(HttpStatus.OK)
    public List<VaccineResponse> getByProtectionEndDateBetween(
            @RequestParam("start_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("end_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        // This method returns the vaccines by protection end date between the given dates.
        List<Vaccine> vaccines = this.vaccineService.findByProtectionEndDateBetween(startDate, endDate);
        return vaccines.stream()
                .map(vaccine -> this.modelMapper.forResponse().map(vaccine, VaccineResponse.class))
                .collect(Collectors.toList());
    }

    @PutMapping("/vaccinate")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AnimalResponse> vaccinate(@Valid @RequestBody VaccinationRequest vaccinationRequest) {
        Animal animal = this.animalService.get(vaccinationRequest.getAnimalId());
        Vaccine newVaccine = this.vaccineService.get(vaccinationRequest.getVaccineId());

        // Check if the animal has a vaccine and if its protection end date is after the current date
        if (animal.getVaccine() != null && animal.getVaccine().getProtectionEndDate().isAfter(LocalDate.now())) {
            return ResultHelper.vaccineProtectionDateNotArrived();
        }

        animal.setVaccine(newVaccine);
        Animal updatedAnimal = this.animalService.update(animal.getId(), animal);
        return ResultHelper.success(this.modelMapper.forResponse().map(updatedAnimal, AnimalResponse.class));
    }
}
