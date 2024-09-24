package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class User {

    private Long id;
    @NotBlank
    @Email(message = "Введите в формате Email, @ - обязательна")
    private String email;
    @NotBlank(message = "Логин не может быть пустым")
    @Pattern(regexp = "\\S+", message = "поле не может содержать пробелы")
    private String login;
    String name;
    @NotNull(message = "Поле даты рождения не должно быть пустым")
    @PastOrPresent(message = "Вы не могли родиться в будущем о.о")
    private LocalDate birthday;
}
