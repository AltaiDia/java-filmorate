package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.utility.classes.NextId;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
@Slf4j
@RestController()
@RequestMapping("/users")
public class UserController {
    private final Map<Long, User> users = new HashMap<>();

    @GetMapping
    public Collection<User> findAll() {
        return users.values();
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
      log.info("Получен запрос POST / тело объекта : {}", user);
        user.setId(NextId.getNextId(users));
        if (user.getName().isBlank()){
            user.setName(user.getLogin());
        }
        users.put(user.getId(), user);
        log.info("Новый пользователь успешно сохранен / тело объекта : {}", user);
        return user;
    }

    @PutMapping
    public User update(@Valid @RequestBody User newUser) {
        log.info("Получен запрос PUT / тело объекта : {}", newUser);
        if(users.containsKey(newUser.getId())){
            users.put(newUser.getId(), newUser);
            log.info("Информация о пользователе обновлена / тело объекта : {}", newUser);
            return newUser;
        }
        throw new NotFoundException("Пользователь с id = " + newUser.getId() + " не найден");
    }
}
