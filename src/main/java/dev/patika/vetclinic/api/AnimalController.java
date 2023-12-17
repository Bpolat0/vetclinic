package dev.patika.vetclinic.api;

import dev.patika.vetclinic.business.abstracts.IAnimalService;
import dev.patika.vetclinic.business.abstracts.ICustomerService;
import dev.patika.vetclinic.business.abstracts.IVaccineService;
import dev.patika.vetclinic.core.config.modelMapper.IModelMapperService;
import dev.patika.vetclinic.core.result.Result;
import dev.patika.vetclinic.core.result.ResultData;
import dev.patika.vetclinic.core.utilies.Msg;
import dev.patika.vetclinic.core.utilies.ResultHelper;
import dev.patika.vetclinic.dto.request.animal.AnimalSaveRequest;
import dev.patika.vetclinic.dto.request.animal.AnimalUpdateRequest;
import dev.patika.vetclinic.dto.request.vaccine.VaccineSaveRequest;
import dev.patika.vetclinic.dto.response.CursorResponse;
import dev.patika.vetclinic.dto.response.animal.AnimalResponse;

import dev.patika.vetclinic.dto.response.customer.CustomerResponse;
import dev.patika.vetclinic.dto.response.vaccine.VaccineResponse;
import dev.patika.vetclinic.entities.Animal;
import dev.patika.vetclinic.entities.Customer;
import dev.patika.vetclinic.entities.Vaccine;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/animals")
public class AnimalController { // This class contains methods that control the Animal endpoints.
    private final IAnimalService animalService;
    private final IModelMapperService modelMapper;
    private final ICustomerService customerService;
    private final IVaccineService vaccineService;

    public AnimalController(IAnimalService animalService, IModelMapperService modelMapper, ICustomerService customerService, IVaccineService vaccineService) {
        this.animalService = animalService;
        this.modelMapper = modelMapper;
        this.customerService = customerService;
        this.vaccineService = vaccineService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<AnimalResponse> save(@Valid @RequestBody AnimalSaveRequest animalSaveRequest) {
        // This method saves the animal.
        Animal saveAnimal = this.modelMapper.forRequest().map(animalSaveRequest, Animal.class);

        Customer customer = this.customerService.get(animalSaveRequest.getCustomerId());
        saveAnimal.setCustomer(customer);

        this.animalService.save(saveAnimal);
        return ResultHelper.created(this.modelMapper.forResponse().map(saveAnimal, AnimalResponse.class));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AnimalResponse> get(@PathVariable("id") Long id) {
        // This method gets the animal by id.
        Animal animal = this.animalService.get(id);
        return ResultHelper.success(this.modelMapper.forResponse().map(animal, AnimalResponse.class));
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<AnimalResponse>> cursor(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        // This method returns the customers with pagination.
        Page<Animal> animals = this.animalService.cursor(page, size);
        Page<AnimalResponse> animalResponsePage = animals
                .map(customer -> this.modelMapper.forResponse().map(animals, AnimalResponse.class));

        return ResultHelper.cursor(animalResponsePage);
    }

    @GetMapping("/{id}/customer")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CustomerResponse> getCustomer(@PathVariable("id") Long id) {
        // This method gets the animal's customer by animal id.
        Animal animal = this.animalService.get(id);
        return ResultHelper.success(this.modelMapper.forResponse().map(animal.getCustomer(), CustomerResponse.class));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AnimalResponse> update(@PathVariable("id") Long id, @Valid @RequestBody AnimalSaveRequest animalSaveRequest) {
        // Map the request to an Animal object
        Animal animal = this.modelMapper.forRequest().map(animalSaveRequest, Animal.class);

        // Call the update method in the AnimalManager class
        Animal updatedAnimal = this.animalService.update(id, animal);

        // Return the updated animal
        return ResultHelper.success(this.modelMapper.forResponse().map(updatedAnimal, AnimalResponse.class));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") Long id) {
        // This method deletes the animal by id.
        this.animalService.delete(id);
        return ResultHelper.ok();
    }

    @GetMapping("/filter")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AnimalResponse>> filterByName(@RequestParam("name") String name) {
        List<Animal> animals = this.animalService.findByNameContainingIgnoreCase(name);
        if (animals.isEmpty()) {
            return ResultHelper.animalNotFoundError();
        }
        List<AnimalResponse> animalResponses = animals.stream()
                .map(animal -> this.modelMapper.forResponse().map(animal, AnimalResponse.class))
                .collect(Collectors.toList());
        return ResultHelper.success(animalResponses);
    }

    @GetMapping("/vaccines")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AnimalResponse>> getAnimalsByVaccineDateRange(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        List<Animal> animals = this.animalService.findAllByVaccineProtectionStartDateGreaterThanEqualAndVaccineProtectionEndDateLessThanEqual(startDate, endDate);
        if (animals.isEmpty()) {
            return ResultHelper.animalNotFoundError();
        }
        List<AnimalResponse> animalResponses = animals.stream()
                .map(animal -> this.modelMapper.forResponse().map(animal, AnimalResponse.class))
                .collect(Collectors.toList());
        return ResultHelper.success(animalResponses);
    }


}
