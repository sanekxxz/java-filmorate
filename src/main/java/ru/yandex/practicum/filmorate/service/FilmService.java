package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmService {

    private static final Logger log = LoggerFactory.getLogger(FilmService.class);
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public Film addFilmsLike(Long id, Long userId) {
        if (!userStorage.existsById(userId)) {
            throw new NotFoundException("Пользователя нет");
        }
        Film film = filmStorage.findById(id);
        film.getLikeUser().add(userId);

        log.info("Пользователь: {} поставили лайк фильму: {}", userId, id);
        return film;
    }

    public Film deleteLike(Long id, Long userId) {
        if (!userStorage.existsById(userId)) {
            throw new NotFoundException("Пользователя нет");
        }

        Film film = filmStorage.findById(id);

        film.getLikeUser().remove(userId);

        log.info("Пользователь: {} удалил лайк фильма: {}", userId, id);
        return film;
    }

    public Collection<Film> returnFilms(Long count) {
        if (count == null) {
            return filmStorage.findAll().stream()
                    .limit(10)
                    .collect(Collectors.toSet());
        }
        return filmStorage.findAll().stream()
                .sorted(Comparator.comparingLong((Film film) -> film.getLikeUser().size()).reversed())
                .limit(count)
                .collect(Collectors.toList());
    }
}
