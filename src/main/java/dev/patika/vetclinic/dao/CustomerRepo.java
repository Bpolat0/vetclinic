package dev.patika.vetclinic.dao;

import dev.patika.vetclinic.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> { // This interface contains CRUD operations for Customer objects.

    @Query("SELECT c FROM Customer c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))") // This method finds the customers by name.
    List<Customer> findByNameContainingIgnoreCase(@Param("name") String name);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

}
