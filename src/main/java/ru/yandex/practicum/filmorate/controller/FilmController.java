package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.utility.classes.NextId;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController()
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {

    private final NextId nextId;
    private final Map<Long, Film> films = new HashMap<>();

    @GetMapping
    public Collection<Film> findAll() {
        return films.values();
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        log.info("Получен запрос POST / тело объекта : {}", film);
        film.setId(nextId.getNextId());
        films.put(film.getId(), film);
        log.info("Фильм успешно сохранен / тело объекта : {}", film);
        return film;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film newFilm) {
        log.info("Получен запрос PUT / тело объекта : {}", newFilm);
        if (films.containsKey(newFilm.getId())) {
            films.put(newFilm.getId(), newFilm);
            log.info("Информация о фильме обновлена / тело объекта : {}", newFilm);
            return newFilm;
        }
        throw new NotFoundException("Фильм с id = " + newFilm.getId() + " не найден");
    }
}
