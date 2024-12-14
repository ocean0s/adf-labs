package com.mtu.lab5.repositories;

import com.mtu.lab5.entities.Household;
import com.mtu.lab5.entities.Pet;
import com.mtu.lab5.repositories.dtos.NameBreed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, BigInteger> {

    @Transactional
    @Modifying
    @Query("UPDATE Pet p SET p.name = :newName, p.animalType = :newAnimalType, p.breed = :newBreed, p.age = :newAge, p.household = :newHousehold WHERE p.id = :id")
    void updatePet(@Param("id") BigInteger id, @Param("newName") String newName, @Param("newAnimalType") String newAnimalType, @Param("newBreed") String newBreed, @Param("newAge") int newAge,@Param("newHousehold") Household household);

    @Transactional
    @Modifying
    @Query("DELETE FROM Pet p WHERE p.name = :name")
    int deleteAllByName(@Param("name") String name);

    @Query("SELECT p FROM Pet p WHERE p.animalType = :animalType")
    List<Pet> findByAnimalType(@Param("animalType") String animalType);

    @Query("SELECT p FROM Pet p WHERE p.breed = :breed")
    List<Pet> findByBreed(@Param("breed")String breed);

    @Query("SELECT new com.mtu.lab5.repositories.dtos.NameBreed(p.name, p.breed) FROM Pet p")
    List<NameBreed> findAllNameBreed();

    Pet findTopByOrderByAgeDesc();

    @Query("SELECT AVG(p.age) FROM Pet p")
    double findAverageAge();
}


