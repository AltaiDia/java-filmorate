package ru.yandex.practicum.filmorate.custom.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class AfterDateValidator implements ConstraintValidator<AfterDate, LocalDate> {
    private LocalDate borderDate;

    @Override
    public void initialize(AfterDate afterDateAnnotation) {
        borderDate = LocalDate.parse(afterDateAnnotation.value());
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        return (value != null) && (value.isAfter(borderDate));
    }
}
