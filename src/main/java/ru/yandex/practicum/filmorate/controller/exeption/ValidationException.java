package ru.yandex.practicum.filmorate.controller.exeption;

public class ValidationException extends Exception{
    public ValidationException(String message) {
        super(message);
    }
}
