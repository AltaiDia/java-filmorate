package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.custom.annotations.AfterDate;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class Film {

    private Long id;
    @NotBlank(message = "Название фильма не должно быть пустым")
    private String name;
    @NotBlank
    @Size(max = 200, message = "Длинна описания не должна быть более 200 символов")
    private String description;
    @NotNull(message = "Дата фильма не должна быть пустой")
    @AfterDate
    private LocalDate releaseDate;
    @NotNull
    @Positive(message = "продолжительность фильма не должна быть отрицательной")
    private Long duration;
    private final Set<Long> listUsersLike = new HashSet<>();
}
