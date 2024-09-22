package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.custom.annotations.AfterDate;

import java.time.LocalDate;

@Data
@Builder
public class Film {

    Long id;
    @NotBlank(message = "Название фильма не должно быть пустым")
    String name;
    @Size(max = 200, message = "Длинна описания не должна быть более 200 символов")
    String description;
    @NotNull(message = "Дата фильма не должна быть пустой")
    @AfterDate
    LocalDate releaseDate;
    @Positive(message = "продолжительность фильма не должна быть отрицательной")
    Long duration;
}
