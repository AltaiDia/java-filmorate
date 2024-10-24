package ru.yandex.practicum.filmorate.storage.user;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
        if (users.containsKey(id)) {
            return users.get(id);
        }
        throw new NotFoundException("Пользователь с id = " + id + " не найден");
    }

    @Override
    public void deleteUser(long id) {
        if (users.containsKey(id)) {
            users.remove(id);
        }
        throw new NotFoundException("Пользователь с id = " + id + " не найден");
    }

    @Override
    public Collection<User> findAllUsers() {
        return users.values();
    }

    public User modificationUser(User newUser) {
        if (users.containsKey(newUser.getId())) {
            newUser = nameCheck(newUser);
            users.put(newUser.getId(), newUser);
            log.info("Информация о пользователе обновлена / тело объекта : {}", newUser);
            return newUser;
        }
        throw new NotFoundException("Пользователь с id = " + newUser.getId() + " не найден");
    }

    public User nameCheck(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        return user;
    }
}
