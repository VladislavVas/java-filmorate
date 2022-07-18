package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface LikeDAO {
    void addLike(long filmId, long userId);
    void deleteLike(long filmId, long userId);
    List<Film> getPopularFilms(long count);
}
