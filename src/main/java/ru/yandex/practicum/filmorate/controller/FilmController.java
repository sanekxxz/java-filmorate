package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {

    private final FilmStorage filmStorage;
    private final FilmService filmService;

    @PutMapping("/{id}/like/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Film addFilmsLike(@PathVariable(required = false, value = "id") Long id,
                             @PathVariable(required = false, value = "userId") Long userId) {
        return filmService.addFilmsLike(id, userId);
    }

    @GetMapping("/{id}")
    public Film findFilm(@PathVariable(required = false, value = "id") Long id) {
        return filmStorage.findById(id);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Film deleteLike(@PathVariable(required = false, value = "id") Long id,
                             @PathVariable(required = false, value = "userId") Long userId) {
        return filmService.deleteLike(id, userId);
    }

    @GetMapping("/popular")
    private Collection<Film> returnFilms(@RequestParam(required = false) Long count) {
        return filmService.returnFilms(count);
    }


    @GetMapping
    public Collection<Film> findAll() {
        return filmStorage.findAll();
    }

    @PostMapping
    public Film create(@Valid @RequestBody(required = false) final Film film) {
        return filmStorage.create(film);
    }

    @PutMapping
    public Film update(@Valid @RequestBody final Film film) {
        return filmStorage.update(film);
    }
}
