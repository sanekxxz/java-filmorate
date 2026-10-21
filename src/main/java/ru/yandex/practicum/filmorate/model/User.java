package ru.yandex.practicum.filmorate.model;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Past;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(of = {"email", "login"})
public class User {
    /**
     * ID - пользователя.
     */
    private Long id;
    /**
     * Email - пользователя.
     */
    @Email(message = "Некорректный формат электронной почты.")
    @NotBlank(message = "Электронная почта не может быть пустой.")
    private String email;
    /**
     * Login - пользователя.
     */
    @NotBlank(message = "Логин не может быть пустым.")
    @Pattern(regexp = "\\S+", message = "Логин не может содержать пробелы.")
    private String login;
    /**
     * Имя - пользователя.
     */
    private String name;
    /**
     * Дата рождения пользователя.
     */
    @NotNull
    @Past(message = "Дата рождения не может быть в будущем.")
    private LocalDate birthday;
}
