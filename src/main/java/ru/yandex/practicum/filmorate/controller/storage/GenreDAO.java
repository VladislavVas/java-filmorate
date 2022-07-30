package ru.yandex.practicum.filmorate.controller.storage;

import ru.yandex.practicum.filmorate.controller.model.Genres;

import java.util.Collection;

public interface GenreDAO {

    Collection<Genres> getAll();
    Genres get(int id);
}