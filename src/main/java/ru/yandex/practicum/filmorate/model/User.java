package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class User {

    Long id;
    @Email(message = "Введите в формате Email, @ - обязательна")
    String email;
    @NotBlank(message = "Логин не может быть пустым")
    @Pattern(regexp = "\\S+", message = "поле не может содержать пробелы")
    String login;
    String name;
    @Past
    LocalDate birthday;
}
