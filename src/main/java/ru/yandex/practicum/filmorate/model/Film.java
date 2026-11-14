package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.yandex.practicum.filmorate.validation.DateAfterOrEqual;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(of = "name")
public class Film {
    private static final int MAX_DESC_LENGTH = 200;
    private Long id;
    @NotNull
    @NotBlank(message = "Название не может быть пустым.")
    private String name;

    @Size(max = MAX_DESC_LENGTH, message = "Максимальная длина 200 символов.")
    @NotNull
    private String description;

    @NotNull
    @DateAfterOrEqual(value = "1895-12-28")
    private LocalDate releaseDate;

    @Positive(message = "Продолжительность должна быть положительной.")
    private int duration;

    Set<Long> likeUser = new HashSet<>();
}
