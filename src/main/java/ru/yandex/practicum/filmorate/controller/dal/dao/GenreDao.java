package ru.yandex.practicum.filmorate.controller.dal.dao;

import ru.yandex.practicum.filmorate.controller.model.Film;
import ru.yandex.practicum.filmorate.controller.model.Genres;

import java.util.List;

public interface GenreDao {

    List<Genres> getAll();

    Genres get(int id);

    void saveGenre(Film film);

}
