package ru.varino.lab1.entity;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Coordinates {
    private Long id;

    @NotNull
    @DecimalMin(value = "-59", inclusive = false)
    private Double x;

    private float y;
}
