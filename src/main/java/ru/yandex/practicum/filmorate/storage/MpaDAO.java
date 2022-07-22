package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collection;

public interface MpaDAO {
    Collection<Mpa> getAll();
    Mpa get(int id);
    Film setFilmMpa(Film film);
}
