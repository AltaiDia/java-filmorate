package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.utility.classes.NextId;

import java.util.Collection;
import java.util.Map;

@Slf4j
@RestController()
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final NextId nextId;
    private final InMemoryUserStorage userStorage;
    private final UserService userService;

    @GetMapping
    public Collection<User> findAll() {
        return userStorage.findAllUsers();
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        log.info("Получен запрос POST / тело объекта : {}", user);
        user.setId(nextId.getNextId());
        user = userStorage.nameCheck(user);
        userStorage.addUser(user);
        log.info("Новый пользователь успешно сохранен / тело объекта : {}", user);
        return user;
    }

    @PutMapping
    public User update(@Valid @RequestBody User newUser) {
        log.info("Получен запрос PUT / тело объекта : {}", newUser);
        return userStorage.modificationUser(newUser);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public User addFrend(@PathVariable Map<String, String> idMap) {
        log.info("Получен запрос PUT / добавить в друзья пользователей : {}", idMap.get("id") +
                " " + idMap.get("friendId"));
        return userService.addFrend(idMap.get("id"), idMap.get("friendId"));
    }


    @DeleteMapping(value = "/{id}/friends/{friendId}")
    public User deleteFrend(@PathVariable Map<String, String> idMap) {
        log.info("Получен запрос DELETE / удалить из друзей пользователей : {}", idMap.get("id") +
                " " + idMap.get("friendId"));
        return userService.deleteFend(idMap.get("id"), idMap.get("friendId"));
    }

    @GetMapping("/{id}/friends")
    public Collection<User> getFrends(@PathVariable("id") String userId) {
        log.info("Получен запрос GET / список друзей пользователя : {}", userId);
        return userService.getListFriends(userId);
    }

    @GetMapping(value = "/{id}/friends/common/{otherId}")
    public Collection<User> getCommonFrends(@PathVariable Map<String, String> idMap) {
        log.info("Получен запрос GET / список общих друзей  у пользователей : {}", idMap.get("id") +
                " " + idMap.get("friendId"));
        return userService.getListCommonFriends(idMap.get("id"), idMap.get("otherId"));
    }
}

