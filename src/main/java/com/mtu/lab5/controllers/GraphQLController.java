package com.mtu.lab5.controllers;

import com.mtu.lab5.controllers.validation.NewHousehold;
import com.mtu.lab5.entities.Household;
import com.mtu.lab5.entities.Pet;
import com.mtu.lab5.services.HouseholdService;
import com.mtu.lab5.services.PetService;
import com.mtu.lab5.services.dtos.PetStatistics;
import com.mtu.lab5.services.exceptions.BadDataException;
import com.mtu.lab5.services.exceptions.NotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.math.BigInteger;
import java.util.List;

@Controller
@AllArgsConstructor
public class GraphQLController {

    private HouseholdService householdService;
    private PetService petService;

    @QueryMapping
    public List<Household> findAllHouseholds() {
        return householdService.findAllHouseholds();
    }

    @QueryMapping
    public List<Pet> findPetsByAnimalType(@Argument String animalType) {
        return petService.findPetsByAnimalType(animalType);
    }

    @QueryMapping
    public Household findHouseholdByEircode(@Argument String eircode) {
        return householdService.findByEircode(eircode).orElseThrow(() -> new NotFoundException("Household not found"));
    }

    @QueryMapping
    public Pet findPetById(@Argument int id) {
        return petService.findPetById(new BigInteger(String.valueOf(id))).orElseThrow(() -> new NotFoundException("Pet not found"));
    }

    @QueryMapping
    public PetStatistics findStatistics() {
        return petService.getPetsStatistics();
    }

    @MutationMapping
    public Household createHousehold(@Valid @Argument("household") NewHousehold newHousehold) {
        Household h = new Household(newHousehold.eircode(), newHousehold.numberOfOccupants() , newHousehold.maxNumberOfOccupants(), newHousehold.ownerOccupied(), null);
        return householdService.createHousehold(h).orElseThrow(() -> new BadDataException("Data for household is not valid or collision with existing data"));
    }

    @MutationMapping
    public int deleteHouseholdByEircode(@Argument String eircode) {
        householdService.deleteByEircode(eircode);
        return 1;
    }

    @MutationMapping
    public int deletePetById(@Argument int id) {
        petService.deletePet(new BigInteger(String.valueOf(id)));
        return 1;
    }

}
