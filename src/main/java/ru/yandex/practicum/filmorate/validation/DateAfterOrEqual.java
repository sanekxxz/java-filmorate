package ru.yandex.practicum.filmorate.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import jakarta.validation.Constraint;

import jakarta.validation.Payload;

@Constraint(validatedBy = DateAfterOrEqualValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DateAfterOrEqual {
	String value();

	String message() default "Дата слишком ранняя";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
