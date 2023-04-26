package ru.yandex.practicum.filmorate.controller.dal.dao;
import ru.yandex.practicum.filmorate.controller.exeption.ValidationException;
import ru.yandex.practicum.filmorate.controller.model.Film;

import java.util.List;

public interface FilmDao {

    List<Film> getAll();

    List<Film> getPopular(Integer count);

    Film create(Film film) throws ValidationException;
    Film updateFilm(Film film) throws ValidationException;
    Film getFilm(Long id);
    void deleteFilm(Long id);

    Film setLikes(Film film);
}
