package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.utility.classes.NextId;

import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserService userService;
    private final NextId nextId;

    public Film createFilm(Film film) {
        film.setId(nextId.getNextId());
        filmStorage.addFilm(film);
        log.info("Фильм успешно сохранен / тело объекта : {}", film);
        return film;
    }

    public Film getFilm(long id) {
        return filmStorage.getFilm(id)
                .orElseThrow(() -> new NotFoundException("Фильм с id = " + id + " не найден"));
    }

    public Film updateFilm(Film newFilm) {
        getFilm(newFilm.getId());
        filmStorage.addFilm(newFilm);
        log.info("Информация о фильма обновлена / тело объекта : {}", newFilm);
        return newFilm;
    }

    public Collection<Film> findFilms() {
        return filmStorage.findAllFilms();
    }

    public Film addLike(long filmId, long userId) {
        getFilm(filmId)
                .getListUsersLike()
                .add(userService
                        .getUser(userId)
                        .getId());
        log.info("Лайк успешно добавлен / тело объекта : {}", getFilm(filmId));
        return getFilm(filmId);
    }

    public Film deleteLike(long filmId, long userId) {
        getFilm(filmId)
                .getListUsersLike()
                .remove(userService
                        .getUser(userId)
                        .getId());
        log.info("Лайк успешно удален / тело объекта : {}", getFilm(filmId));
        return getFilm(filmId);
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
