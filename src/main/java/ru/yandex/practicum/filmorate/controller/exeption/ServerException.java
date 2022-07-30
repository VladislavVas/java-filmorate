package ru.yandex.practicum.filmorate.controller.exeption;

public class ServerException extends RuntimeException{

    public ServerException(String message) {
        super(message);
    }
}
