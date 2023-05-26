package ru.yandex.practicum.filmorate.controller.dal.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.dal.dao.MpaDao;
import ru.yandex.practicum.filmorate.controller.dal.mappers.MpaMapper;
import ru.yandex.practicum.filmorate.controller.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.controller.model.Film;
import ru.yandex.practicum.filmorate.controller.model.Mpa;

import java.util.List;


@Component
@RequiredArgsConstructor
public class MpaDaoImpl implements MpaDao {

    private final JdbcTemplate jdbcTemplate;

    private static final String GET_MPA = "SELECT * FROM mpa WHERE mpa_id = ?";
    private static final String GET_ALL_MPA = "SELECT * FROM mpa";
    private static final String SET_MPA = "INSERT INTO film_mpa (film_id, mpa_id) VALUES (?, ?)";
    private static final String DELETE_MPA = "DELETE FROM FILM_MPA WHERE FILM_ID = ?";

    @Override
    public List<Mpa> getAll() {
        return jdbcTemplate.query(GET_ALL_MPA, new MpaMapper());
    }

    @Override
    public Mpa get(int id) throws NotFoundException {
        return jdbcTemplate.queryForObject(GET_MPA, new MpaMapper(), id);
    }

    @Override
    public Film setMpa(Film film) {
        cleanTable(film.getId());
        film.getMpa().getId();
        if (film.getMpa() != null && film.getMpa().getId() != 0) {
            jdbcTemplate.update(SET_MPA, film.getId(), film.getMpa().getId());
        }
        return film;
    }

    private void cleanTable(Long id) {
        jdbcTemplate.update(DELETE_MPA, id);
    }

}

