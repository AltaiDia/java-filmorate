package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Validator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;


@SpringBootTest
public class UserControllerTests {
    @Autowired
    private Validator validator;

    @Test
    void failValidateEmpty() {
        User user = User.builder().build();
        final var violations = validator.validate(user);
        Assertions.assertThat(violations).isNotEmpty();
    }

    @Test
    void failValidateEmail() {
        User user = User.builder()
                .email("23e32efddf")
                .login("Name")
                .birthday(LocalDate.of(2000, 2, 2))
                .build();

        final var violations = validator.validate(user);
        Assertions.assertThat(violations).isNotEmpty();
    }

    @Test
    void failValidateLoginNotBlank() {
        User user = User.builder()
                .email("r@yandex.ru")
                .login("   ")
                .birthday(LocalDate.of(2000, 2, 2))
                .build();
        final var violations = validator.validate(user);
        Assertions.assertThat(violations).isNotEmpty();
    }

    @Test
    void failValidateLoginPatter() {
        User user = User.builder()
                .email("r@yandex.ru")
                .login("Na me")
                .birthday(LocalDate.of(2000, 2, 2))
                .build();
        final var violations = validator.validate(user);
        Assertions.assertThat(violations).isNotEmpty();
    }

    @Test
    void failValidateBirthdayPast() {
        User user = User.builder()
                .email("r@yandex.ru")
                .login("Name")
                .birthday(LocalDate.of(23000, 2, 2))
                .build();
        final var violations = validator.validate(user);
        Assertions.assertThat(violations).isNotEmpty();
    }
}
