package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {
    void addUser(User user);

    User getUser(long id);

    Collection<User> findAllUsers();

    void deleteUser(long id);
}
