package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;

public interface GenreDAO {

    Collection<Genre> getAll();
    Genre get(int id);
}
