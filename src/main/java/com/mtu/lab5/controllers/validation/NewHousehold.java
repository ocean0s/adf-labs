package com.mtu.lab5.controllers.validation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record NewHousehold(@NotBlank(message = "Invalid eircode") @NotNull(message = "Invalid eircode") String eircode,
                           @PositiveOrZero(message = "Number of occupants cannot be smaller than 0") int numberOfOccupants,
                           @PositiveOrZero(message = "Max number of occupants cannot be smaller than 0") int maxNumberOfOccupants,
                           boolean ownerOccupied) {
}
