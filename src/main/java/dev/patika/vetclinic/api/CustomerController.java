package dev.patika.vetclinic.api;

import dev.patika.vetclinic.business.abstracts.IAnimalService;
import dev.patika.vetclinic.business.abstracts.ICustomerService;
import dev.patika.vetclinic.core.config.modelMapper.IModelMapperService;
import dev.patika.vetclinic.core.result.Result;
import dev.patika.vetclinic.core.result.ResultData;
import dev.patika.vetclinic.core.utilies.ResultHelper;
import dev.patika.vetclinic.dto.request.customer.CustomerSaveRequest;
import dev.patika.vetclinic.dto.request.customer.CustomerUpdateRequest;
import dev.patika.vetclinic.dto.response.CursorResponse;
import dev.patika.vetclinic.dto.response.animal.AnimalResponse;
import dev.patika.vetclinic.dto.response.customer.CustomerResponse;
import dev.patika.vetclinic.entities.Animal;
import dev.patika.vetclinic.entities.Customer;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/customers")
public class CustomerController { // This class contains methods that control the Customer endpoints.

    private final ICustomerService customerService;
    private final IAnimalService animalService;
    private final IModelMapperService modelMapper;

    public CustomerController(ICustomerService customerService, IAnimalService animalService, IModelMapperService modelMapper) {
        // This constructor provides us to access the methods of the CustomerService class.
        this.customerService = customerService;
        this.animalService = animalService;
        this.modelMapper = modelMapper;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<CustomerResponse> save(@Valid @RequestBody CustomerSaveRequest customerSaveRequest) {
        // This method saves the customer.
        Customer saveCustomer = this.modelMapper.forRequest().map(customerSaveRequest, Customer.class);
        ResultData<Customer> result = this.customerService.save(saveCustomer);

        // Check if a customer with the same email or phone already exists
        if (!result.isSuccess()) {
            return new ResultData<>(false, result.getMessage(), "400", null);
        }

        return ResultHelper.created(this.modelMapper.forResponse().map(result.getData(), CustomerResponse.class));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CustomerResponse> update(@Valid @RequestBody CustomerUpdateRequest customerUpdateRequest) {
        // Map the request to a Customer object
        Customer customer = this.modelMapper.forRequest().map(customerUpdateRequest, Customer.class);

        // Call the update method in the CustomerManager class
        ResultData<Customer> resultData = this.customerService.update(customerUpdateRequest.getId(), customer);

        // If the update was not successful, return the error message
        if (!resultData.isSuccess()) {
            return new ResultData<>(false, resultData.getMessage(), "400", null);
        }

        // Map the updated customer to a CustomerResponse object
        CustomerResponse customerResponse = this.modelMapper.forResponse().map(resultData.getData(), CustomerResponse.class);

        // Return the updated customer
        return ResultHelper.success(customerResponse);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CustomerResponse> get(@PathVariable("id") Long id) {
        // This method gets the customer by id.
        Customer customer = this.customerService.get(id);
        return ResultHelper.success(this.modelMapper.forResponse().map(customer, CustomerResponse.class));
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<CustomerResponse>> cursor(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        // This method returns the customers with pagination.
        Page<Customer> customers = this.customerService.cursor(page, size);
        Page<CustomerResponse> customerResponsePage = customers
                .map(customer -> this.modelMapper.forResponse().map(customer, CustomerResponse.class));

        return ResultHelper.cursor(customerResponsePage);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") Long id) {
        // This method deletes the customer by id.
        this.customerService.delete(id);
        return ResultHelper.ok();
    }

    @GetMapping("/{id}/animal")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<AnimalResponse>> getCustomer(
            @PathVariable("id") Long id,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        // This method returns the animals by customer id.
        Page<Animal> animals = this.animalService.cursor(page, size);
        List<Animal> animalList = this.animalService.findByCustomerId(id);
        List<AnimalResponse> animalResponseList = new ArrayList<>();
        for (Animal animal : animalList) {
            animalResponseList.add(this.modelMapper.forResponse().map(animal, AnimalResponse.class));
        }
        Page<AnimalResponse> animalResponsePage = animals
                .map(animal -> this.modelMapper.forResponse().map(animal, AnimalResponse.class));
        return ResultHelper.cursor(animalResponsePage);
    }

    @GetMapping("/filterByName")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<CustomerResponse>> getCustomersByName(@RequestParam("name") String name) {
        // This method returns the customers by name.
        List<Customer> customers = this.customerService.getCustomersByName(name);
        List<CustomerResponse> customerResponses = customers.stream()
                .map(customer -> this.modelMapper.forResponse().map(customer, CustomerResponse.class))
                .collect(Collectors.toList());
        return ResultHelper.success(customerResponses);
    }
}
