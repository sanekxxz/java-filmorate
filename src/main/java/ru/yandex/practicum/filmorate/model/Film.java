package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.yandex.practicum.filmorate.validation.DateAfterOrEqual;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(of = "name")
public class Film {
    /**
     * Максимальная длина описания фильма.
     */
    private static final int MAX_DESC_LENGTH = 200;
    /**
     * ID - Фильма.
     */
    private Long id;
    /**
     * Название фильма.
     */
    @NotNull
    @NotBlank(message = "Название не может быть пустым.")
    private String name;
    /**
     * Описание фильма.
     */
    @Size(max = MAX_DESC_LENGTH, message = "Максимальная длина 200 символов.")
    private String description;
    /**
     * Дата выпуска фильма.
     */
    @NotNull
    @DateAfterOrEqual(value = "1895-12-28")
    private LocalDate releaseDate;
    /**
     * Продолжительность фильма.
     */
    @Min(value = 0, message = "Число должно быть положительным.")
    private int duration;
}
