package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    /**
     * Содержим все добавленные фильмы.
     */
    private final Map<Long, Film> films = new HashMap<>();

    /**
     * Возвращает список всех фильмов.
     *
     * @return коллекцию фильмов
     */
    @GetMapping
    public Collection<Film> findAll() {
        return films.values();
    }

    /**
     * Создания фильма.
     *
     * @param film фильм для добавления
     * @return новый фильм
     */
    @PostMapping
    public Film create(@Valid @RequestBody final Film film) {

        if (films.containsValue(film)) {
            throw new ValidationException("Такой фильм уже есть");
        }

        film.setId(getNextId());
        films.put(film.getId(), film);
        log.info("Фильм создан");
        return film;
    }

    /**
     * Редактирование фильма.
     *
     * @param film фильм с обновлёнными данными
     * @return фильм, но с обновленными данными
     */
    @PutMapping
    public Film update(@Valid @RequestBody final Film film) {

        if (films.containsKey(film.getId())) {
            Film oldFilm = films.get(film.getId());

            if (!oldFilm.getDescription().equals(film.getDescription())) {
                oldFilm.setDescription(film.getDescription());
            }

            if (!oldFilm.getName().equals(film.getName())) {
                oldFilm.setName(film.getName());
            }

            if (oldFilm.getDuration() != film.getDuration()) {
                oldFilm.setDuration(film.getDuration());
            }

            if (!oldFilm.getReleaseDate().equals(film.getReleaseDate())) {
                oldFilm.setReleaseDate(film.getReleaseDate());
            }
            log.info("Данные фильма обновленны");
            return oldFilm;
        }

        throw new NotFoundException("Такого фильма нет");
    }

    /**
     * ID для фильма самое последнее и доступное.
     *
     * @return max значение увеличенное на +1
     */
    private long getNextId() {
        long currentMaxId = films.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
