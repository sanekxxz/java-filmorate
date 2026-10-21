package ru.yandex.practicum.filmorate.exception;

public class ValidationException extends RuntimeException {

    public ValidationException(final String exception) {
        super(exception);
    }
}
