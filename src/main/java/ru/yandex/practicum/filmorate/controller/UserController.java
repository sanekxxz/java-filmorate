package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserStorage userStorage;
    private final UserService userService;

    @GetMapping
    public Collection<User> findAll() {
        return userStorage.findAll();
    }

    @PutMapping("/{id}/friends/{friendId}")
    @ResponseStatus(HttpStatus.OK)
    public User addFriends(@PathVariable(required = false, value = "id") Long id,
                           @PathVariable(required = false, value = "friendId") Long friendId) {
        return userService.addFriends(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public User deleteFriends(@PathVariable(required = false, value = "id") Long id,
                           @PathVariable(required = false, value = "friendId") Long friendId) {
        return userService.deleteFriends(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public Collection<User> findAllFriends(@PathVariable(required = false, value = "id") Long id) {
        return userService.findAllFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    @ResponseStatus(HttpStatus.OK)
    public Collection<User> listFriendsCommon(@PathVariable(required = false, value = "id") Long id,
                                              @PathVariable(required = false, value = "otherId") Long otherId) {
        return userService.listFriendsCommon(id, otherId);
    }

    @PostMapping
    public User create(@Valid @RequestBody final User user) {
        return userStorage.create(user);
    }

    @PutMapping
    public User update(@Valid @RequestBody final User newUser) {
        return userStorage.update(newUser);
    }
}
