package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;

    public User addFrend(String usId, String frId) {
        long userId = Long.parseLong(usId);
        long friendId = Long.parseLong(frId);
        userStorage.getUser(userId)
                .getListFrendsId()
                .add(friendId);

        userStorage.getUser(friendId)
                .getListFrendsId()
                .add(userId);
        log.info("Друг успешно добавлен / тело объекта : {}", userStorage.getUser(userId));
        return userStorage.getUser(userId);
    }

    public User deleteFend(String usId, String frId) {
        long userId = Long.parseLong(usId);
        long friendId = Long.parseLong(frId);
        userStorage.getUser(userId)
                .getListFrendsId()
                .remove(friendId);

        userStorage.getUser(friendId)
                .getListFrendsId()
                .remove(userId);
        log.info("Друг успешно удален / тело объекта : {}", userStorage.getUser(userId));
        return userStorage.getUser(userId);
    }

    public Collection<User> getListCommonFriends(String usId, String otId) {
        long userId = Long.parseLong(usId);
        long otherId = Long.parseLong(otId);
        List<User> commanFrendsList = userStorage.getUser(otherId).getListFrendsId()
                .stream()
                .filter(id -> userStorage.getUser(userId).getListFrendsId().contains(id))
                .map(userStorage::getUser)
                .toList();
        log.info("Список успешно сформирован / тело объекта : {}", commanFrendsList);
        return commanFrendsList;
    }

    public Collection<User> getListFriends(String usId) {
        long userId = Long.parseLong(usId);
        List<User> friendsList = userStorage.getUser(userId).getListFrendsId()
                .stream()
                .map(userStorage::getUser)
                .toList();
        log.info("Список успешно сформирован / тело объекта : {}", friendsList);
        return friendsList;
    }
}
