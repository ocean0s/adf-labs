package com.mtu.lab5.services;

import com.mtu.lab5.entities.Household;
import com.mtu.lab5.entities.Pet;
import com.mtu.lab5.repositories.dtos.NameBreed;
import com.mtu.lab5.services.dtos.PetStatistics;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface PetService {
    public Optional<Pet> addPet(Pet pet);
    public List<Pet> findAllPets() ;
    public Optional<Pet> findPetById(BigInteger id) ;
    public void updatePet(BigInteger id, String name, String animalType, String breed, int age, Household household);
    public boolean deletePetsByName(String name);
    public boolean deletePet(BigInteger id);
    public List<Pet> findPetsByAnimalType(String animalType);
    public List<Pet> findPetsByBreed(String breed);
    public List<NameBreed> findAllNameBreed();
    public PetStatistics getPetsStatistics();
}
