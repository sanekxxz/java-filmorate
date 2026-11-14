package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {
    User update(User user);

    User findById(Long id);

    User create(User film);

    User delete(Long id);

    Collection<User> findAll();

    Boolean existsById(Long id);
}
