package com.mtu.lab5.controllers.validation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record NewPet(@NotNull @NotBlank String name,
                     @NotNull @NotBlank String animalType,
                     @NotNull @NotBlank String breed,
                     @PositiveOrZero int age,
                     String eircode) {
}
