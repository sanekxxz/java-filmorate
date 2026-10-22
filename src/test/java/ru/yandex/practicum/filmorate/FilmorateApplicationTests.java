package ru.yandex.practicum.filmorate;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Set;

@SpringBootTest
class FilmorateApplicationTests {

    private Set<ConstraintViolation<User>> valUser;
    private Set<ConstraintViolation<Film>> valFilm;

    @Test
    void validatorsAllCorrectUser() {
        User user = new User();
        user.setLogin("null");
        user.setEmail("email@ru");
        user.setName("user");
        user.setBirthday(LocalDate.of(1999, 9, 26));

        valUser = Validation.buildDefaultValidatorFactory().getValidator().validate(user);

        valUser.forEach(userConstraintViolation -> System.out.println(userConstraintViolation.getPropertyPath()
                + ": " + userConstraintViolation.getMessage()));

        assertTrue(valUser.isEmpty());
    }

    @Test
    void validatorsNullAndBlank() {
        User user = new User();
        user.setLogin(null);
        user.setEmail("email@ru");
        user.setName("");
        user.setBirthday(LocalDate.of(1999, 9, 26));

        valUser = Validation.buildDefaultValidatorFactory().getValidator().validate(user);

        valUser.forEach(userConstraintViolation -> System.out.println(userConstraintViolation.getPropertyPath()
                + ": " + userConstraintViolation.getMessage()));

        assertFalse(valUser.isEmpty());
    }

    @Test
    void validatorsIncorrectEmail() {
        User user = new User();
        user.setLogin("null");
        user.setEmail("это-неправильный?эмейл@");
        user.setName("");
        user.setBirthday(LocalDate.of(1999, 9, 26));

        valUser = Validation.buildDefaultValidatorFactory().getValidator().validate(user);

        valUser.forEach(userConstraintViolation -> System.out.println(userConstraintViolation.getPropertyPath()
                + ": " + userConstraintViolation.getMessage()));

        assertFalse(valUser.isEmpty());
    }

    @Test
    void validatorsDate() {
        User user = new User();
        user.setLogin("null");
        user.setEmail("email@ru");
        user.setName("");
        user.setBirthday(LocalDate.of(2027, 9, 26));

        valUser = Validation.buildDefaultValidatorFactory().getValidator().validate(user);

        valUser.forEach(userConstraintViolation -> System.out.println(userConstraintViolation.getPropertyPath()
                + ": " + userConstraintViolation.getMessage()));

        assertFalse(valUser.isEmpty());
    }

    @Test
    void validatorsCreateFilms() {
        Film film = new Film();
        film.setName("Терминатор");
        film.setDescription("Роботы железные");
        film.setDuration(30);
        film.setReleaseDate(LocalDate.of(1895, 12, 28));

        valFilm = Validation.buildDefaultValidatorFactory().getValidator().validate(film);

        valFilm.forEach(userConstraintViolation -> System.out.println(userConstraintViolation.getPropertyPath()
                + ": " + userConstraintViolation.getMessage()));

        assertTrue(valFilm.isEmpty());
    }

    @Test
    void validatorsNullAndBlankFilm() {
        Film film = new Film();
        film.setName(null);
        film.setDescription("");
        film.setDuration(30);
        film.setReleaseDate(LocalDate.of(2000, 8, 20));

        valFilm = Validation.buildDefaultValidatorFactory().getValidator().validate(film);

        valFilm.forEach(userConstraintViolation -> System.out.println(userConstraintViolation.getPropertyPath()
                + ": " + userConstraintViolation.getMessage()));

        assertFalse(valFilm.isEmpty());

    }

    @Test
    void validatorsMaxLength200() {
        Film film = new Film();
        film.setName("null");
        film.setDescription("ф".repeat(201));
        film.setDuration(30);
        film.setReleaseDate(LocalDate.of(2000, 8, 20));

        valFilm = Validation.buildDefaultValidatorFactory().getValidator().validate(film);

        valFilm.forEach(userConstraintViolation -> System.out.println(userConstraintViolation.getPropertyPath()
                + ": " + userConstraintViolation.getMessage()));

        assertFalse(valFilm.isEmpty());
    }

    @Test
    void validatorsNegativeValueDuration() {
        Film film = new Film();
        film.setName("null");
        film.setDescription("ф".repeat(199));
        film.setDuration(-1);
        film.setReleaseDate(LocalDate.of(2000, 8, 20));

        valFilm = Validation.buildDefaultValidatorFactory().getValidator().validate(film);

        valFilm.forEach(userConstraintViolation -> System.out.println(userConstraintViolation.getPropertyPath()
                + ": " + userConstraintViolation.getMessage()));

        assertFalse(valFilm.isEmpty());
    }

    @Test
    void validatorsDateFilm() {
        Film film = new Film();
        film.setName("null");
        film.setDescription("ф".repeat(199));
        film.setDuration(1);
        film.setReleaseDate(LocalDate.of(1894, 12, 28));

        valFilm = Validation.buildDefaultValidatorFactory().getValidator().validate(film);

        valFilm.forEach(userConstraintViolation -> System.out.println(userConstraintViolation.getPropertyPath()
                + ": " + userConstraintViolation.getMessage()));

        assertFalse(valFilm.isEmpty());
    }
}
