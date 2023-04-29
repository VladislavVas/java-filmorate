package ru.yandex.practicum.filmorate.controller.dal.dao;

import ru.yandex.practicum.filmorate.controller.model.Film;
import ru.yandex.practicum.filmorate.controller.model.Mpa;

import java.util.List;

public interface MpaDao {

    List<Mpa> getAll();

    Mpa get(int id);

    Film setFilmMpa(Film film);

}
