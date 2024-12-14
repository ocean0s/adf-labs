package com.mtu.lab5.repositories;

import com.mtu.lab5.entities.Household;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface HouseholdRepository extends JpaRepository<Household, String> {

    @EntityGraph(attributePaths = "pets")
    Optional<Household> findByEircode(String eircode);

    @Query("SELECT h FROM Household h WHERE SIZE(h.pets) = 0")
    List<Household> findEmptyHouseholds();

    @Transactional
    @Modifying
    @Query("DELETE FROM Household h WHERE h.eircode = :eircode")
    void deleteHouseholdByEircode(@Param("eircode") String eircode);
}
