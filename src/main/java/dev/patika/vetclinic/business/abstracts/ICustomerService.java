package dev.patika.vetclinic.business.abstracts;

import dev.patika.vetclinic.core.result.ResultData;
import dev.patika.vetclinic.dao.CustomerRepo;
import dev.patika.vetclinic.entities.Customer;
import dev.patika.vetclinic.entities.Vaccine;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ICustomerService { // This interface includes the methods that the CustomerManager class will implement.

    ResultData<Customer> save(Customer customer);

    Customer get(Long id);

    boolean delete(Long id);

    ResultData<Customer> update(Long id, Customer customer);

    Page<Customer> cursor(int page, int size);

    List<Customer> getCustomersByName(String name);

}
