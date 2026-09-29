package ru.varino.lab1.entity;

import jakarta.validation.constraints.Min;
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
    @Min(-58)
    private Integer x;

    private float y;
}
