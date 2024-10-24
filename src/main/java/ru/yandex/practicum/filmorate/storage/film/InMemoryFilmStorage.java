package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Long, Film> films = new HashMap<>();

    @Override
    public void addFilm(Film film) {
        films.put(film.getId(), film);

    }

    @Override
    public Collection<Film> findAllFilms() {
        return films.values();
    }

    @Override
    public Film getFilm(long id) {
        if (films.containsKey(id)) {
            return films.get(id);
        }
        throw new NotFoundException("Фильм с id = " + id + " не найден");
    }

    @Override
    public void deleteFilm(long id) {
        if (films.containsKey(id)) {
            films.remove(id);
        } else {
            throw new NotFoundException("Фильм с id = " + id + " не найден");
        }
    }

    public Film modificationFilm(Film newFilm) {
        if (films.containsKey(newFilm.getId())) {
            films.put(newFilm.getId(), newFilm);
            log.info("Информация о фильме обновлена / тело объекта : {}", newFilm);
            return newFilm;
        }
        throw new NotFoundException("Фильм с id = " + newFilm.getId() + " не найден");
    }
}
