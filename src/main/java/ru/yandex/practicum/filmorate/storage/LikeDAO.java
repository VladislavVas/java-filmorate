package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Set;

public interface LikeDAO {
    void addLike(long filmId, long userId);
    void deleteLike(long filmId, long userId);
}
