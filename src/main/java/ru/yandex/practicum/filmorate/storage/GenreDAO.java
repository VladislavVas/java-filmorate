package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Genres;

import java.util.Collection;

public interface GenreDAO {

    Collection<Genres> getAll();
    Genres get(int id);
}
