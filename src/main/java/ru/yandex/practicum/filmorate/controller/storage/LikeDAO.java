package ru.yandex.practicum.filmorate.controller.storage;

public interface LikeDAO {
    void addLike(long filmId, long userId);
    void deleteLike(long filmId, long userId);
}
