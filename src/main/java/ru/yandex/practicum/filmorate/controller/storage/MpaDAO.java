package ru.yandex.practicum.filmorate.controller.storage;

import ru.yandex.practicum.filmorate.controller.model.Film;
import ru.yandex.practicum.filmorate.controller.model.Mpa;

import java.util.Collection;

public interface MpaDAO {
    Collection<Mpa> getAll();
    Mpa get(int id);
    Film setFilmMpa(Film film);
}
