package ru.varino.lab1.entity;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Person {
    private Long id;

    @NotBlank
    private String name;

    private Color eyeColor;

    private Color hairColor;

    @Valid
    @NotNull
    private Location location;

    @Positive
    private Integer height;

    private Country nationality;
}
