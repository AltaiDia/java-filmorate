package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public Film addLike(String fId, String usId) {
        long filmId = Long.parseLong(fId);
        long userId = Long.parseLong(usId);
        if (userStorage.getUser(userId) == null) {
            throw new NotFoundException("Пользователь с id = " + usId + " не найден");
        }
        filmStorage.getFilm(filmId)
                .getListUsersLike()
                .add(userId);
        log.info("Лайк успешно добавлен / тело объекта : {}", filmStorage.getFilm(filmId));
        return filmStorage.getFilm(filmId);
    }

    public Film deleteLike(String fId, String usId) {
        long filmId = Long.parseLong(fId);
        long userId = Long.parseLong(usId);
        if (userStorage.getUser(userId) == null) {
            throw new NotFoundException("Пользователь с id = " + usId + " не найден");
        }
        filmStorage.getFilm(filmId)
                .getListUsersLike()
                .remove(userId);
        log.info("Лайк успешно удален / тело объекта : {}", filmStorage.getFilm(filmId));
        return filmStorage.getFilm(filmId);
    }

    public List<Film> getTopFilms(String c) {
        int count = Integer.parseInt(c);
        List<Film> filmList = filmStorage.findAllFilms().stream()
                .sorted((f1, f2) -> f2.getListUsersLike().size() - f1.getListUsersLike().size())
                .limit(count)
                .toList();
        log.info("Топ фильмов успешно сформирован / тело объекта : {}", filmList);
        return filmList;
    }
}
