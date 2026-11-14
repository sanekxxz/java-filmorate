package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.InternalServerException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStorage userStorage;

    public User addFriends(Long id, Long friendId) {
        User user = userStorage.findById(id);
        User friend = userStorage.findById(friendId);

        if (id.equals(friendId)) {
            throw new InternalServerException("Нельзя добавить самого себя");
        }

        user.getFriend().add(friendId);
        friend.getFriend().add(id);

        log.info("Друг добавлен");
        return  user;
    }

    public User deleteFriends(Long id, Long friendId) {
        User user = userStorage.findById(id);
        User friend = userStorage.findById(friendId);

        user.getFriend().remove(friendId);
        friend.getFriend().remove(id);

        log.info("Друг удалён");
        return user;
    }

    public Collection<User> findAllFriends(Long id) {
        return userStorage.findById(id).getFriend().stream()
                .map(userStorage::findById)
                .collect(Collectors.toList());
    }

    public Collection<User> listFriendsCommon(Long id, Long otherId) {
        User user = userStorage.findById(id);
        User userOther = userStorage.findById(otherId);

        return user.getFriend().stream()
                .filter(friendId -> userOther.getFriend().contains(friendId))
                .map(userStorage::findById)
                .collect(Collectors.toList());
    }
}
