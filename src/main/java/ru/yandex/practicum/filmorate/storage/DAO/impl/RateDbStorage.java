package ru.yandex.practicum.filmorate.storage.DAO.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Rate;
import ru.yandex.practicum.filmorate.storage.DAO.impl.mappers.RateMapper;
import ru.yandex.practicum.filmorate.storage.RateDAO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Slf4j
@Component
public class RateDbStorage implements RateDAO {

    private final JdbcTemplate jdbcTemplate;

    public RateDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Collection<Rate> getAll() {
        final String sql = "SELECT * FROM RATE";
        return jdbcTemplate.query(sql, new RateMapper());
    }

    @Override
    public Rate get(int id) throws NotFoundException{
        if (id < 0 || id > 5){
            throw new NotFoundException("Неизвестный ркйтинг");
        } else {
        final String sql = "SELECT * FROM RATE WHERE RATE_ID = ?";
        final List<Rate> rateList = jdbcTemplate.query(sql, new RateMapper(), id);
        Rate rate = rateList.get(0);
        return rate;}
    }

    public Film setFilmRate (Film film){
        cleanTable(film.getId());
        if (film.getRate() != null){
                String sql = "INSERT INTO FILM_RATE (FILM_ID, RATE_ID) VALUES (?, ?)";
                jdbcTemplate.update(sql, film.getId(), film.getRate());
        }
        return film;
    }

    private void cleanTable(Long id){
        String sql = "DELETE FROM FILM_RATE WHERE FILM_ID = ?";
        jdbcTemplate.update(sql, id);
    }
}

