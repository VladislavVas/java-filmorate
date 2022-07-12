package ru.yandex.practicum.filmorate.storage;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface FilmStorage {

    List<Film> getAll();
    Film create(Film film) throws ValidationException;
    Film put(Film film) throws ValidationException;
    Film getFilm(Long id);
    void deleteFilm(Long id);
}
