package ru.varino.lab1.entity;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Person {
    private Long id;

    @NotNull
    @Size(min = 1)
    private String name;

    private Color eyeColor;

    private Color hairColor;

    @Valid
    @NotNull
    private Location location;

    @Positive
    private Double height;

    private Country nationality;
}
