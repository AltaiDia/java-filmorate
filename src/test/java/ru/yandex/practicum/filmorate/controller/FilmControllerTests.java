package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Validator;
import lombok.Value;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

@SpringBootTest
public class FilmControllerTests {

    @Autowired
    private Validator validator;
    @Test
    void failValidateEmpty() {
        Film film = Film.builder().build();
        final var violations = validator.validate(film);
        Assertions.assertThat(violations).isNotEmpty();
    }
    @Test
    void failValidateNameNotBlank() {
        Film film = Film.builder()
                .name("    ")
                .description("Описание")
                .releaseDate(LocalDate.of(2000, 10, 10))
                .duration(100L)
                .build();
        final var violations = validator.validate(film);
        Assertions.assertThat(violations).isNotEmpty();
    }
    @Test
    void failValidateDescriptionSize() throws IOException, URISyntaxException {
        Film film = Film.builder()
                .name("Фильм")
                .description(Files.readString(Paths.get(getClass().getClassLoader().getResource("description.txt").toURI())))
                .releaseDate(LocalDate.of(2000, 10, 10))
                .duration(100L)
                .build();
        final var violations = validator.validate(film);
        Assertions.assertThat(violations).isNotEmpty();
    }
    @Test
    void failValidateReleaseDateNotNull(){
        Film film = Film.builder()
                .name("Фильм")
                .description("Описание")
                .releaseDate(null)
                .duration(100L)
                .build();
        final var violations = validator.validate(film);
        Assertions.assertThat(violations).isNotEmpty();
    }
    @Test
    void failValidateReleaseDateAfterDate(){
        Film film = Film.builder()
                .name("Фильм")
                .description("Описание")
                .releaseDate(LocalDate.of(1000, 10, 10))
                .duration(100L)
                .build();
        final var violations = validator.validate(film);
        Assertions.assertThat(violations).isNotEmpty();
    }
    @Test
    void failValidatePositiveDuration(){
        Film film = Film.builder()
                .name("Фильм")
                .description("Описание")
                .releaseDate(LocalDate.of(2000, 10, 10))
                .duration(-100L)
                .build();
        final var violations = validator.validate(film);
        Assertions.assertThat(violations).isNotEmpty();
    }
}
