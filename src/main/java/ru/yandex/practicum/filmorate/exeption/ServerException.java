package ru.yandex.practicum.filmorate.exeption;

public class ServerException extends RuntimeException{

    public ServerException(String message) {
        super(message);
    }
}
