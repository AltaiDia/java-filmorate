package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.utility.classes.NextId;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController()
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {

    private final NextId nextId;
    private final InMemoryFilmStorage filmStorage;
    private final FilmService filmService;

    @GetMapping
    public Collection<Film> findAll() {
        return filmStorage.findAllFilms();
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        log.info("Получен запрос POST / тело объекта : {}", film);
        film.setId(nextId.getNextId());
        filmStorage.addFilm(film);
        log.info("Фильм успешно сохранен / тело объекта : {}", film);
        return film;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film newFilm) {
        log.info("Получен запрос PUT / тело объекта : {}", newFilm);
        return filmStorage.modificationFilm(newFilm);
    }

    @PutMapping(value = "/{id}/like/{userId}")
    public Film addLike(@PathVariable Map<String, String> idMap) {
        log.info("Получен запрос POST / добавить лайк фильму с id : {}", idMap.get("id"));
        return filmService.addLike(idMap.get("id"), idMap.get("userId"));
    }

    @DeleteMapping(value = "/{id}/like/{userId}")
    public Film deleteLike(@PathVariable Map<String, String> idMap) {
        log.info("Получен запрос DELETE / удалить лайк фильму с id : {}", idMap.get("id"));
        return filmService.deleteLike(idMap.get("id"), idMap.get("userId"));
    }

    @GetMapping(value = "/popular")
    public List<Film> findTopFilms(@RequestParam(defaultValue = "10") String count) {
        log.info("Получен запрос GET / топ из : {}", count + " фильмов");
        return filmService.getTopFilms(count);
    }
}
