package com.mtu.lab5.services;

import com.mtu.lab5.entities.Household;

import java.util.List;
import java.util.Optional;

public interface HouseholdService {
    public Optional<Household> findByEircode(String eircode);
    // public Optional<Household> findByEircodeEager(String eircode);
    public List<Household> findEmptyHouseholds();
    public List<Household> findAllHouseholds();
    public void deleteByEircode(String eircode);
    public Optional<Household> createHousehold(Household household);
}
