package com.mtu.lab5.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "household")
@AllArgsConstructor
@NoArgsConstructor
public class Household {
    @Id
    @Column(unique = true, nullable = false)
    private String eircode;
    @Column(nullable = false)
    private int numberOfOccupants;
    @Column(nullable = false)
    private int maxNumberOfOccupants;
    @Column(nullable = false)
    private boolean ownerOccupied;

    @OneToMany(mappedBy = "household", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Pet> pets;

}
