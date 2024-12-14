package com.mtu.lab5;

import com.mtu.lab5.entities.Household;
import com.mtu.lab5.entities.Pet;
import com.mtu.lab5.services.HouseholdService;
import com.mtu.lab5.services.PetService;
import com.mtu.lab5.services.dtos.PetStatistics;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class Lab5ApplicationTests {

	@Autowired
	private PetService petService;
	@Autowired
	private HouseholdService householdService;

	@Test
	public void testAddPet_ValidPet() {
		Household h = householdService.findByEircode("A94B6F3").orElseThrow( () -> new IllegalStateException("Illegal state in test!"));
		Pet pet = new Pet("Buddy", "Dog", "Golden Retriever", 3, h);
		Optional<Pet> result = petService.addPet(pet);
		assertTrue(result.isPresent());
		assertEquals("Buddy", result.get().getName());
	}

	@Test
	public void testAddPet_NullName() {
		Household h = householdService.findByEircode("A94B6F3").orElseThrow( () -> new IllegalStateException("Illegal state in test!"));
		Pet pet = new Pet(null, "Dog", "Golden Retriever", 3, h);
		assertThrows(IllegalArgumentException.class, () -> {
			petService.addPet(pet);
		});
	}

	@Test
	public void testGetAllPets_EmptyRepository() {
		List<Pet> result = petService.findAllPets();
		assertFalse(result.isEmpty());
	}

	@Test
	public void testUpdatePet_ValidUpdate() {
		BigInteger id = BigInteger.valueOf(1); // Assume this ID exists after adding a pet
		Household h = householdService.findByEircode("A94B6F3").orElseThrow( () -> new IllegalStateException("Illegal state in test!"));
		Pet p = new Pet(id, "Max", "Dog", "Labrador", 4, h);
		petService.updatePet(id, "Max", "Dog", "Labrador", 4, h);
		assertTrue(petService.findPetById(id).isPresent());
		assertEquals("Max", petService.findPetById(id).get().getName());
	}

	@Test
	public void testDeletePet_ExistingId() {
		BigInteger id = BigInteger.valueOf(1); // Assume this ID exists after adding a pet
		boolean result = petService.deletePet(id);
		assertTrue(result);
	}

	@Test
	public void testDeletePet_NonExistingId() {
		BigInteger id = BigInteger.valueOf(9999); // Non-existing ID
		boolean result = petService.deletePet(id);
		assertFalse(result);
	}

	@Test
	public void testStatistics() {
		PetStatistics result = petService.getPetsStatistics();
		System.out.println(result);
	}
}

