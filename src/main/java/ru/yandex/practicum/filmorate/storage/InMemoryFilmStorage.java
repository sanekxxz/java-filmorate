package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Long, Film> films = new HashMap<>();

    @Override
    public Collection<Film> findAll() {
        return films.values();
    }

    @Override
    public Film findById(Long id) {
        Film film = films.get(id);

        if (film == null) {
            throw new NotFoundException("Фильм не найден");
        }

        return film;
    }

    @Override
    public Film delete(Long id) {
        if (!films.containsKey(id)) {
            throw new ValidationException("Такого фильма нет");
        }
        return films.remove(id);
    }

    @Override
    public Boolean existsById(Long id) {
        return films.containsKey(id);
    }

    @Override
    public Film create(Film film) {

        if (films.containsValue(film)) {
            throw new ValidationException("Такой фильм уже есть");
        }

        film.setId(getNextId());
        films.put(film.getId(), film);
        log.info("Фильм создан");
        return film;
    }


    @Override
    public Film update(Film film) {

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

    private long getNextId() {
        long currentMaxId = films.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
