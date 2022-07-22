package ru.yandex.practicum.filmorate.storage;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface FilmStorage {

    List<Film> getAll();

    List<Film> getPopular(Integer count);

    Film create(Film film) throws ValidationException;
    Film updateFilm(Film film) throws ValidationException;
    Film getFilm(Long id);
    void deleteFilm(Long id);

    Film setLikes(Film film);
}
