package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;

@Slf4j
@RestController()
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public Collection<User> findAll() {
        return userService.findUsers();
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        log.info("Получен запрос POST / тело объекта : {}", user);
        return userService.createUser(user);
    }

    @PutMapping
    public User update(@Valid @RequestBody User newUser) {
        log.info("Получен запрос PUT / тело объекта : {}", newUser);
        return userService.updateUser(newUser);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public User addFriend(@PathVariable("id") long id, @PathVariable("friendId") long friendId) {
        log.info("Получен запрос PUT / добавить в друзья пользователей : {} и {}", id, friendId);
        return userService.addFriend(id, friendId);
    }


    @DeleteMapping(value = "/{id}/friends/{friendId}")
    public User deleteFriend(@PathVariable("id") long id, @PathVariable("friendId") long friendId) {
        log.info("Получен запрос DELETE / удалить из друзей пользователей : {} и {}", id,
                friendId);
        return userService.deleteFend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public Collection<User> getFriends(@PathVariable("id") long id) {
        log.info("Получен запрос GET / список друзей пользователя : {}", id);
        return userService.getListFriends(id);
    }

    @GetMapping(value = "/{id}/friends/common/{otherId}")
    public Collection<User> getCommonFriends(@PathVariable("id") long id, @PathVariable("otherId") long otherId) {
        log.info("Получен запрос GET / список общих друзей  у пользователей : {} и {}", id, otherId);
        return userService.getListCommonFriends(id, otherId);
    }
}

