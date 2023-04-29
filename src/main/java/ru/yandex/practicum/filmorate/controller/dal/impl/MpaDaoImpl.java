package ru.yandex.practicum.filmorate.controller.dal.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.dal.dao.MpaDao;
import ru.yandex.practicum.filmorate.controller.dal.mappers.MpaMapper;
import ru.yandex.practicum.filmorate.controller.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.controller.model.Film;
import ru.yandex.practicum.filmorate.controller.model.Mpa;

import java.util.List;


@Slf4j
@Component
@RequiredArgsConstructor
public class MpaDaoImpl implements MpaDao {

    private final JdbcTemplate jdbcTemplate;

    private final String GET_MPA = "SELECT * FROM MPA WHERE MPA_ID = ?";
    private final String GET_ALL_MPA = "SELECT * FROM MPA";

    private final String SET_MPA = "INSERT INTO FILM_MPA (FILM_ID, MPA_ID) VALUES (?, ?)";

    @Override
    public List<Mpa> getAll() {
        return jdbcTemplate.query(GET_ALL_MPA, new MpaMapper());
    }

    @Override
    public Mpa get(int id) throws NotFoundException {
        return jdbcTemplate.queryForObject(GET_MPA, new MpaMapper(), id);
    }

    @Override
    public Film setFilmMpa(Film film) {
        cleanTable(film.getId());
        film.getMpa().getId();
        if (film.getMpa() != null && film.getMpa().getId() != 0) {
            jdbcTemplate.update(SET_MPA, film.getId(), film.getMpa().getId());
        }
        return film;
    }

    private void cleanTable(Long id) {
        String sql = "DELETE FROM FILM_MPA WHERE FILM_ID = ?";
        jdbcTemplate.update(sql, id);
    }
}

