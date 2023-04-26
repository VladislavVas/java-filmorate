package ru.yandex.practicum.filmorate.controller.dal.dao;

import ru.yandex.practicum.filmorate.controller.model.Genres;

import java.util.Collection;

public interface GenreDao {

    Collection<Genres> getAll();
    Genres get(int id);
}
