package ru.yandex.practicum.filmorate.storage.user;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Slf4j
@Component
@Data
public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> users = new HashMap<>();

    @Override
    public void addUser(User user) {
        users.put(user.getId(), user);
    }

    @Override
    public User getUser(long id) {
        return Optional.ofNullable(users.get(id)).
                orElseThrow(() -> new NotFoundException("Пользователь с id = " + id + " не найден"));
    }

    @Override
    public void deleteUser(long id) {
        if (users.remove(id) == null) {
            throw new NotFoundException("Пользователь с id = " + id + " не найден");
        }

    }

    @Override
    public Collection<User> findAllUsers() {
        return List.copyOf(users.values());
    }
}
