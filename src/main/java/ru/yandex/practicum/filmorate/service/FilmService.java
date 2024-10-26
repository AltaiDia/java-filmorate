package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.utility.classes.NextId;

import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final NextId nextId;

    public Film createFilm(Film film) {
        film.setId(nextId.getNextId());
        filmStorage.addFilm(film);
        log.info("Фильм успешно сохранен / тело объекта : {}", film);
        return film;
    }

    public Film updateFilm(Film newFilm) {
        if (filmStorage.findAllFilms().stream()
                .anyMatch(film -> film.getId()
                        .equals(newFilm.getId()))) {
            filmStorage.addFilm(newFilm);
            return newFilm;
        }
        throw new NotFoundException("Фильм с id = " + newFilm.getId() + " не найден");
    }

    public Collection<Film> findFilms() {
        return filmStorage.findAllFilms();
    }

    public Film addLike(long filmId, long userId) {
        if (userStorage.getUser(userId) == null) {
            throw new NotFoundException("Пользователь с id = " + userId + " не найден");
        }
        filmStorage.getFilm(filmId)
                .getListUsersLike()
                .add(userId);
        log.info("Лайк успешно добавлен / тело объекта : {}", filmStorage.getFilm(filmId));
        return filmStorage.getFilm(filmId);
    }

    public Film deleteLike(long filmId, long userId) {
        ;
        if (userStorage.getUser(userId) == null) {
            throw new NotFoundException("Пользователь с id = " + userId + " не найден");
        }
        filmStorage.getFilm(filmId)
                .getListUsersLike()
                .remove(userId);
        log.info("Лайк успешно удален / тело объекта : {}", filmStorage.getFilm(filmId));
        return filmStorage.getFilm(filmId);
    }

    public List<Film> getTopFilms(int count) {
        List<Film> filmList = filmStorage.findAllFilms().stream()
                .sorted((f1, f2) -> f2.getListUsersLike().size() - f1.getListUsersLike().size())
                .limit(count)
                .toList();
        log.info("Топ фильмов успешно сформирован / тело объекта : {}", filmList);
        return filmList;
    }
}
