package com.mtu.lab5.controllers;

import com.mtu.lab5.controllers.validation.NewHousehold;
import com.mtu.lab5.controllers.validation.NewPet;
import com.mtu.lab5.entities.Household;
import com.mtu.lab5.entities.Pet;
import com.mtu.lab5.services.HouseholdService;
import com.mtu.lab5.services.PetService;
import com.mtu.lab5.services.exceptions.BadDataException;
import com.mtu.lab5.services.exceptions.NotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/myapi")
@AllArgsConstructor
public class WebController {

    private PetService petService;
    private HouseholdService householdService;

    // get all the pets => also implicit with /api/pets
    @GetMapping({"pets", "pets/"})
    List<Pet> getPets() {
        return petService.findAllPets();
    }

    // get all the households => implicit with /api/households
    @GetMapping({"households", "households/"})
    List<Household> getHouseholds() {
        return householdService.findAllHouseholds();
    }
    // get households with no pets
    @GetMapping({"empty-households", "empty-households/"})
    List<Household> getEmptyHouseholds(){
        return householdService.findEmptyHouseholds();
    }

    // get a pet => implicit with /api/pets/:id
    @GetMapping("pets/{id}")
    Pet getPetById(@PathVariable("id") BigInteger id) {
        return petService.findPetById(id).orElseThrow(() -> new NotFoundException("Pet not found"));
    }

    // get a household => implicit with /api/households/:id (eircode)
    @GetMapping({"households/{id}", "households/{id}/"})
    Household getHouseholdById(@PathVariable("id") String eircode) {
        return householdService.findByEircode(eircode).orElseThrow(() -> new NotFoundException("Household not found"));
    }

    // delete a pet
    @DeleteMapping({"pets/{id}", "pets/{id}/"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deletePetById(@PathVariable("id") BigInteger id) throws NotFoundException {
        petService.deletePet(id);
    }

    // delete a household
    @DeleteMapping({"households/{id}", "households/{id}/"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteHouseholdById(@PathVariable("id") String eircode) throws NotFoundException {
        householdService.deleteByEircode(eircode);
    }

    // create a new household (POST)
    @PostMapping({"households", "households/"})
    @ResponseStatus(HttpStatus.CREATED)
    Household addHousehold(@RequestBody @Valid NewHousehold newHousehold) throws BadDataException {
        Household h = new Household(newHousehold.eircode(), newHousehold.numberOfOccupants() , newHousehold.maxNumberOfOccupants(), newHousehold.ownerOccupied(), null);
        return householdService.createHousehold(h).orElseThrow(() -> new BadDataException("New Household not created"));
    }

    // create a new pet (POST)
    @PostMapping({"pets", "pets/"})
    @ResponseStatus(HttpStatus.CREATED)
    Pet addPet(@RequestBody @Valid NewPet newPet) throws BadDataException, NotFoundException {
        Household h = null;
        if (newPet.eircode() != null && !newPet.eircode().isBlank())
            h = householdService.findByEircode(newPet.eircode()).orElseThrow(() -> new NotFoundException("Household not found"));
        Pet p = new Pet(newPet.name(), newPet.animalType(), newPet.breed(), newPet.age(), h);
        return petService.addPet(p).orElseThrow(() -> new BadDataException("New pet not created"));
    }

    // change a pets name (PATCH)
    @PatchMapping({"pets/{id}", "pets/{id}/"})
    @ResponseStatus(HttpStatus.OK)
    void changePetName(@RequestBody String name, @PathVariable("id") BigInteger id) throws NotFoundException, BadDataException {
        if (name == null || name.isBlank())
            throw new BadDataException("Pet name cannot be empty");
        Pet p = petService.findPetById(id).orElseThrow(() -> new NotFoundException("Pet not found"));
        petService.updatePet(p.getId(), name, p.getAnimalType(), p.getBreed(), p.getAge(), p.getHousehold());
    }
}
