package ru.yandex.practicum.filmorate.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;


public class DateAfterOrEqualValidator implements ConstraintValidator<DateAfterOrEqual, LocalDate> {

	private LocalDate minDate;

	@Override
	public void initialize(DateAfterOrEqual constraintAnnotation) {
		minDate = LocalDate.parse(constraintAnnotation.value());
	}

	@Override
	public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
		return !localDate.isBefore(minDate);
	}
}
