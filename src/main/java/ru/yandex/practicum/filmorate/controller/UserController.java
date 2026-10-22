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
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    /**
     * Map - Хранит всех пользователей.
     */
    private final Map<Long, User> users = new HashMap<>();


    /**
     * Метод показывает всех пользователей.
     *
     * @return Возвращает коллекцию пользователей
     */
    @GetMapping
    public Collection<User> findAll() {
        return users.values();
    }

    /**
     * Создания пользователя.
     *
     * @param user пользователь
     * @return возвращает пользователя
     */
    @PostMapping
    public User create(@Valid @RequestBody final User user) {

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

    /**
     * Обновление данных пользователя.
     *
     * @param newUser Обновлённый пользователь
     * @return возвращает пользователя с новыми данными
     */
    @PutMapping
    public User update(@Valid @RequestBody final User newUser) {
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

    /**
     * ID для пользователя самое последнее и доступное.
     *
     * @return max значение увеличенное на +1
     */
    private long getNextId() {
        long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

    /**
     * Проверка Email.
     *
     * @param newUser Пользователь
     * @return true and false
     */
    private boolean equalsEmail(final User newUser) {
        return users.values().stream()
                .anyMatch(user -> user.getEmail()
                        .equalsIgnoreCase(newUser.getEmail())
                        && !user.getId().equals(newUser.getId()));
    }
}
