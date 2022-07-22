package ru.yandex.practicum.filmorate.storage.DAO.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.DAO.impl.mappers.MpaMapper;
import ru.yandex.practicum.filmorate.storage.MpaDAO;

import java.util.List;


@Slf4j
@Component
public class MpaDbStorage implements MpaDAO {

    private final JdbcTemplate jdbcTemplate;

    public MpaDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Mpa> getAll() {
        final String sql = "SELECT * FROM MPA";
        List<Mpa> mpaList =  jdbcTemplate.query(sql, new MpaMapper());
        return mpaList;
    }

    @Override
    public Mpa get(int id) throws NotFoundException{
        if (id < 0 || id > 5){
            throw new NotFoundException("Неизвестный MPA");
        } else {
        final String sql = "SELECT * FROM MPA WHERE MPA_ID = ?";
        Mpa mpa = jdbcTemplate.queryForObject(sql, new MpaMapper(), id);
        return mpa;}
    }

    @Override
    public Film setFilmMpa(Film film){
        cleanTable(film.getId());
        film.getMpa().getId();
        if (film.getMpa() != null && film.getMpa().getId() != 0){
                String sql = "INSERT INTO FILM_MPA (FILM_ID, MPA_ID) VALUES (?, ?)";
                jdbcTemplate.update(sql, film.getId(), film.getMpa().getId());
        }
        return film;
    }

    private void cleanTable(Long id){
        String sql = "DELETE FROM FILM_MPA WHERE FILM_ID = ?";
        jdbcTemplate.update(sql, id);
    }
}

