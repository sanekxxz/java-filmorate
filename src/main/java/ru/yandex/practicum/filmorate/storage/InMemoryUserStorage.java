package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {

    private final Map<Long, User> users = new HashMap<>();


    @Override
    public Collection<User> findAll() {
        return users.values();
    }

    @Override
    public User findById(Long id) {

        User user = users.get(id);

        if(user == null) {
            throw new NotFoundException("Такого пользователя нет");
        }

        return user;
    }

    @Override
    public Boolean existsById(Long id) {
        return users.containsKey(id);
    }

    @Override
    public User delete(Long id) {
        if(!users.containsKey(id)) {
            throw new ValidationException("Такого пользователя нету");
        }
        return users.remove(id);
    }

    @Override
    public User create(User user) {

        if (users.containsValue(user)) {
            throw new ValidationException("Такой пользователь уже есть!");
        }

        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }

        user.setId(getNextId());
        users.put(user.getId(), user);
        log.info("Пользователь создан");
        return user;
    }

    @Override
    public User update(User newUser) {
        if (newUser.getId() == null) {
            throw new ValidationException("Id должен быть указан");
        }
        if (users.containsKey(newUser.getId())) {
            User oldUser = users.get(newUser.getId());
            if (!oldUser.getEmail().equalsIgnoreCase(newUser.getEmail())) {
                if (equalsEmail(newUser)) {
                    throw new ValidationException("Email уже используется");
                }
                oldUser.setEmail(newUser.getEmail());
            }

            if (!oldUser.getLogin().equals(newUser.getLogin())) {
                oldUser.setLogin(newUser.getLogin());
            }
            if (newUser.getName() != null && !newUser.getName().isBlank()) {
                oldUser.setName(newUser.getName());
            }

            if (!oldUser.getBirthday().equals(newUser.getBirthday())) {
                oldUser.setBirthday(newUser.getBirthday());
            }
            log.info("Данные пользователя обновленны");
            return oldUser;
        }
        throw new NotFoundException("Пользователь не найден");
    }


    private long getNextId() {
        long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }


    private boolean equalsEmail(final User newUser) {
        return users.values().stream()
                .anyMatch(user -> user.getEmail()
                        .equalsIgnoreCase(newUser.getEmail())
                        && !user.getId().equals(newUser.getId()));
    }
}
