package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.utility.classes.NextId;

import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;
    private final NextId nextId;

    public Collection<User> findUsers() {
        return userStorage.findAllUsers();
    }

    public User createUser(User user) {
        user.setId(nextId.getNextId());
        nameCheck(user);
        userStorage.addUser(user);
        log.info("Новый пользователь успешно сохранен / тело объекта : {}", user);
        return user;
    }

    public User updateUser(User newUser) {
        if (userStorage.findAllUsers().stream()
                .anyMatch(user -> user.getId()
                        .equals(newUser.getId()))) {
            nameCheck(newUser);
            userStorage.addUser(newUser);
            log.info("Информация о пользователе обновлена / тело объекта : {}", newUser);
            return newUser;
        }
        throw new NotFoundException("Пользователь с id = " + newUser.getId() + " не найден");
    }

    public User addFriend(long userId, long friendId) {
        userStorage.getUser(userId)
                .getListFrendsId()
                .add(friendId);

        userStorage.getUser(friendId)
                .getListFrendsId()
                .add(userId);
        log.info("Друг успешно добавлен / тело объекта : {}", userStorage.getUser(userId));
        return userStorage.getUser(userId);
    }

    public User deleteFend(long userId, long friendId) {
        userStorage.getUser(userId)
                .getListFrendsId()
                .remove(friendId);

        userStorage.getUser(friendId)
                .getListFrendsId()
                .remove(userId);
        log.info("Друг успешно удален / тело объекта : {}", userStorage.getUser(userId));
        return userStorage.getUser(userId);
    }

    public Collection<User> getListCommonFriends(long userId, long otherId) {
        List<User> commanFrendsList = userStorage.getUser(otherId).getListFrendsId()
                .stream()
                .filter(id -> userStorage.getUser(userId).getListFrendsId().contains(id))
                .map(userStorage::getUser)
                .toList();
        log.info("Список успешно сформирован / тело объекта : {}", commanFrendsList);
        return commanFrendsList;
    }

    public Collection<User> getListFriends(long userId) {
        List<User> friendsList = userStorage.getUser(userId).getListFrendsId()
                .stream()
                .map(userStorage::getUser)
                .toList();
        log.info("Список успешно сформирован / тело объекта : {}", friendsList);
        return friendsList;
    }

    private void nameCheck(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}
