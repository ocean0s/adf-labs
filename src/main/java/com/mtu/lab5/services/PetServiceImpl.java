package com.mtu.lab5.services;

import com.mtu.lab5.entities.Household;
import com.mtu.lab5.entities.Pet;
import com.mtu.lab5.repositories.PetRepository;
import com.mtu.lab5.repositories.dtos.NameBreed;
import com.mtu.lab5.services.dtos.PetStatistics;
import com.mtu.lab5.services.exceptions.BadDataException;
import com.mtu.lab5.services.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PetServiceImpl implements PetService{

    private PetRepository petRepository;

    @Transactional
    @Modifying
    public Optional<Pet> addPet(Pet pet) {
        if (pet.getAge() < 0)
            throw new BadDataException("Age must be a positive integer");
        if (pet.getName() == null || pet.getName().isEmpty())
            throw new BadDataException("Name cannot be null or empty");
        if (pet.getAnimalType() == null || pet.getAnimalType().isEmpty())
            throw new BadDataException("AnimalType cannot be null or empty");
        if (pet.getBreed() == null || pet.getBreed().isEmpty())
            throw new BadDataException("Breed cannot be null or empty");
        return Optional.of(petRepository.save(pet));
    }

    public List<Pet> findAllPets() {
        return petRepository.findAll();
    }

    public Optional<Pet> findPetById(BigInteger id) {
        return petRepository.findById(id);
    }

    @Transactional
    @Modifying
    public void updatePet(BigInteger id, String name, String animalType, String breed, int age, Household household) {
        if (age < 0)
            throw new BadDataException("Age must be a positive integer");
        if (name == null || name.isEmpty())
            throw new BadDataException("Name cannot be null or empty");
        if (animalType == null || animalType.isEmpty())
            throw new BadDataException("AnimalType cannot be null or empty");
        if (breed == null || breed.isEmpty())
            throw new BadDataException("Breed cannot be null or empty");
        if (household == null)
            throw new BadDataException("Household cannot be null or empty");
        if (petRepository.findById(id).isPresent())
            petRepository.updatePet(id, name, animalType, breed, age, household);
        else
            throw new NotFoundException("Pet with the id " + id + " not found");
    }

    @Transactional
    @Modifying
    public boolean deletePetsByName(String name) {
        return petRepository.deleteAllByName(name) > 0;
    }

    @Transactional
    @Modifying
    public boolean deletePet(BigInteger id) {
        if (petRepository.findById(id).isPresent()) {
            petRepository.deleteById(id);
            return true;
        }
        throw new NotFoundException("Pet not found with that id");
    }

    public List<Pet> findPetsByAnimalType(String animalType) {
        return petRepository.findByAnimalType(animalType);
    }

    public List<Pet> findPetsByBreed(String breed) {
        return petRepository.findByBreed(breed);
    }

    public List<NameBreed> findAllNameBreed() {
        return petRepository.findAllNameBreed();
    }

    public PetStatistics getPetsStatistics() {
        double averageAge = petRepository.findAverageAge();
        int maxAge = petRepository.findTopByOrderByAgeDesc().getAge();
        return new PetStatistics(maxAge, averageAge);
    }
}
