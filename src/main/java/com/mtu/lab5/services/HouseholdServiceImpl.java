package com.mtu.lab5.services;

import com.mtu.lab5.entities.Household;
import com.mtu.lab5.repositories.HouseholdRepository;
import com.mtu.lab5.services.exceptions.BadDataException;
import com.mtu.lab5.services.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class HouseholdServiceImpl implements HouseholdService {

    private HouseholdRepository householdRepository;

    @Override
    public Optional<Household> findByEircode(String eircode) {
        if (eircode == null || eircode.isBlank())
            throw new BadDataException("Invalid eircode");
        return householdRepository.findByEircode(eircode);
    }

    /*
    @Override
    public Optional<Household> findByEircodeEager(String eircode) {
        if (eircode == null || eircode.isBlank())
            throw new BadDataException("Invalid eircode");
        return householdRepository.findByEircodeEager(eircode);
    }
    */

    @Override
    public List<Household> findEmptyHouseholds() {
        return householdRepository.findEmptyHouseholds();
    }

    @Override
    public List<Household> findAllHouseholds() {
        return householdRepository.findAll();
    }

    @Override
    public void deleteByEircode(String eircode) {
        if (eircode == null || eircode.isBlank())
            throw new BadDataException("Invalid eircode");
        if (householdRepository.findByEircode(eircode).isPresent())
            householdRepository.deleteHouseholdByEircode(eircode);
        else
            throw new NotFoundException("Household to delete not found.");
    }

    @Override
    public Optional<Household> createHousehold(Household household) {
        if (household == null)
            throw new BadDataException("Invalid household");
        if (household.getEircode() == null || household.getEircode().isBlank())
            throw new BadDataException("Invalid eircode");
        if (household.getNumberOfOccupants() < 0)
            throw new BadDataException("Invalid number of occupants");
        if (household.getMaxNumberOfOccupants() < 0)
            throw new BadDataException("Invalid max number of occupants");
        return Optional.of(householdRepository.save(household));
    }

}
