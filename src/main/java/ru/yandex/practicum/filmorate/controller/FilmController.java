package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;
import java.util.List;

@Slf4j
@Validated
@RestController()
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {
    private final FilmService filmService;

    @GetMapping
    public Collection<Film> findAll() {
        return filmService.findFilms();
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        log.info("Получен запрос POST / тело объекта : {}", film);
        return filmService.createFilm(film);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film newFilm) {
        log.info("Получен запрос PUT / тело объекта : {}", newFilm);
        return filmService.updateFilm(newFilm);
    }

    @PutMapping(value = "/{id}/like/{userId}")
    public Film addLike(@PathVariable("id") long id, @PathVariable("userId") long userId) {
        log.info("Получен запрос POST / добавить лайк фильму с id : {}", id);
        return filmService.addLike(id, userId);
    }

    @DeleteMapping(value = "/{id}/like/{userId}")
    public Film deleteLike(@PathVariable("id") long id, @PathVariable("userId") long userId) {
        log.info("Получен запрос DELETE / удалить лайк фильму с id : {}", id);
        return filmService.deleteLike(id, userId);
    }

    @GetMapping(value = "/popular")
    public List<Film> findTopFilms(@RequestParam(defaultValue = "10") @Positive int count) {
        log.info("Получен запрос GET / топ из : {} фильмов", count);
        return filmService.getTopFilms(count);
    }
}
