package dev.patika.vetclinic.business.concretes;

import dev.patika.vetclinic.business.abstracts.ICustomerService;
import dev.patika.vetclinic.core.exception.NotFoundException;
import dev.patika.vetclinic.core.result.ResultData;
import dev.patika.vetclinic.core.utilies.Msg;
import dev.patika.vetclinic.core.utilies.ResultHelper;
import dev.patika.vetclinic.dao.CustomerRepo;
import dev.patika.vetclinic.entities.Customer;
import dev.patika.vetclinic.entities.Vaccine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerManager implements ICustomerService { // This class implements the ICustomerService interface.

    private final CustomerRepo customerRepo;

    public CustomerManager(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    @Override
    public ResultData<Customer> save(Customer customer) {
        // Check if a customer with the same email or phone already exists
        if (customerRepo.existsByEmail(customer.getEmail())) {
            return ResultHelper.EmailExists();
        }
        if (customerRepo.existsByPhone(customer.getPhone())) {
            return ResultHelper.PhoneExists();
        }

        // Save the new customer and return it
        Customer savedCustomer = customerRepo.save(customer);
        return ResultHelper.created(savedCustomer);
    }

    @Override
    public Customer get(Long id) {
        // This method gets the customer by id.
        return this.customerRepo.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
    }

    @Override
    public boolean delete(Long id) {
        // This method deletes the customer by id.
        Customer customer = this.get(id);
        this.customerRepo.delete(customer);
        return true;
    }

    @Override
    public ResultData<Customer> update(Long id, Customer customer) {
        // Check if the customer with the given id exists
        Customer existingCustomer = this.customerRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));

        // Update the details of the existing customer
        existingCustomer.setName(customer.getName());
        existingCustomer.setPhone(customer.getPhone());
        existingCustomer.setEmail(customer.getEmail());
        existingCustomer.setAddress(customer.getAddress());
        existingCustomer.setCity(customer.getCity());

        // Save the updated customer in the database
        Customer updatedCustomer = this.customerRepo.save(existingCustomer);

        // Return the updated customer
        return ResultHelper.success(updatedCustomer);
    }

    @Override
    public Page<Customer> cursor(int page, int size) {
        // This method returns the customers with pagination.
        Pageable pageable = PageRequest.of(page, size);
        return this.customerRepo.findAll(pageable);
    }

    @Override
    public List<Customer> getCustomersByName(String name) {
        // This method returns the customers by name.
        return customerRepo.findByNameContainingIgnoreCase(name);
    }



}
