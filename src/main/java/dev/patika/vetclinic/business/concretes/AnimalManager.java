package dev.patika.vetclinic.business.concretes;

import dev.patika.vetclinic.business.abstracts.IAnimalService;
import dev.patika.vetclinic.core.exception.NotFoundException;
import dev.patika.vetclinic.core.utilies.Msg;
import dev.patika.vetclinic.dao.AnimalRepo;
import dev.patika.vetclinic.entities.Animal;
import dev.patika.vetclinic.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AnimalManager implements IAnimalService { // This class implements the IAnimalService interface.

    private final AnimalRepo animalRepo;

    public AnimalManager(AnimalRepo animalRepo) {
        this.animalRepo = animalRepo;
    }

    @Override
    public Animal save(Animal animal) {
        // This method saves the animal.
        return this.animalRepo.save(animal);
    }

    @Override
    public Animal get(Long id) {
        // This method gets the animal by id.
        return this.animalRepo.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
    }

    @Override
    public boolean delete(Long id) {
        // This method deletes the animal by id.
        Animal animal = this.get(id);
        this.animalRepo.delete(animal);
        return true;
    }

    @Override
    public Animal update(Long id, Animal animal) {
        // Check if the animal with the given ID exists
        Animal existingAnimal = this.animalRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));

        // Update the existing animal with the new data
        existingAnimal.setName(animal.getName());
        existingAnimal.setSpecies(animal.getSpecies());
        existingAnimal.setBreed(animal.getBreed());
        existingAnimal.setGender(animal.getGender());
        existingAnimal.setColor(animal.getColor());
        existingAnimal.setDateOfBirth(animal.getDateOfBirth());
        existingAnimal.setCustomer(animal.getCustomer());

        // Save the updated animal and return it
        return this.animalRepo.save(existingAnimal);
    }

    @Override
    public Page<Animal> cursor(int page, int size) {
        // This method returns the animals with pagination.
        Pageable pageable = PageRequest.of(page, size);
        return this.animalRepo.findAll(pageable);
    }

    @Override
    public List<Animal> findByCustomerId(Long customerId) {
        // This method returns the animals by customer id.
        return this.animalRepo.findAllByCustomerId(customerId);
    }

    @Override
    public List<Animal> findByVaccineId(Long vaccineId) {
        // This method returns the animals by vaccine id.
        return this.animalRepo.findByVaccineId(vaccineId);
    }

    @Override
    public List<Animal> findByNameContainingIgnoreCase(String name) {
        return this.animalRepo.findByNameContainingIgnoreCase(name);
    }

    @Override
    public List<Animal> findAllByVaccineProtectionStartDateGreaterThanEqualAndVaccineProtectionEndDateLessThanEqual(LocalDate startDate, LocalDate endDate) {
        return animalRepo.findAllByVaccineProtectionStartDateGreaterThanEqualAndVaccineProtectionEndDateLessThanEqual(startDate, endDate);
    }


}
