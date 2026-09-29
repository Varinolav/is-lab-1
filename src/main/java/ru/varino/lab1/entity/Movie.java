package ru.varino.lab1.entity;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Movie {
    private Integer id;

    @NotBlank
    private String name;

    @Valid
    @NotNull
    private Coordinates coordinates;

    private LocalDateTime creationDate;

    @PositiveOrZero
    private int oscarsCount;

    @Positive
    private Long budget;

    @Positive
    private Long totalBoxOffice;

    @NotNull
    private MpaaRating mpaaRating;

    @Valid
    @NotNull
    private Person director;

    @Valid
    private Person screenwriter;

    @Valid
    @NotNull
    private Person operator;

    @Positive
    private Long length;

    @Positive
    private Integer goldenPalmCount;

    private MovieGenre genre;
}
