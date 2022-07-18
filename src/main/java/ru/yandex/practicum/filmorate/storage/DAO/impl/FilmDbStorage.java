package ru.yandex.practicum.filmorate.storage.DAO.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Rate;
import ru.yandex.practicum.filmorate.storage.DAO.impl.mappers.FilmMapper;
import ru.yandex.practicum.filmorate.storage.DAO.impl.mappers.GenreMapper;
import ru.yandex.practicum.filmorate.storage.DAO.impl.mappers.RateMapper;
import ru.yandex.practicum.filmorate.storage.DAO.impl.mappers.UserMapper;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.Util.Validator;

import java.util.List;


@Slf4j
@Component
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;
    private final Validator validator;

    @Autowired
    public FilmDbStorage(JdbcTemplate jdbcTemplate, Validator validator) {
        this.jdbcTemplate = jdbcTemplate;
        this.validator = validator;
    }


    @Override
    public List<Film> getAll() {
        String sql = "SELECT * FROM FILMS";
        List <Film> filmsFromDb = jdbcTemplate.query(sql,new FilmMapper());
        for (Film film : filmsFromDb){
            film.setRate(getRate(film));
            film.setGenre(getGenre(film));
        }
        return filmsFromDb;
    }

    @Override
    public Film create(Film film) throws ValidationException {
        validator.filmValidator(film);
        fuckFilmId(film);
        return getFilm(film.getId());
    }

    private Film fuckFilmId (Film film){
        Long id = validator.getId();
        String sql = "INSERT INTO FILMS(FILM_ID, FILM_NAME, DESCRIPTION, RELEASE_DATE, DURATION) values (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                id,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration());
        film.setId(id);
        log.info("Создан фильм id=%", id);
        return film;
    }



    @Override
    public Film updateFilm(Film film) throws ValidationException {
        testId (film.getId());
        validator.filmValidator(film);
            String sql = "UPDATE FILMS SET FILM_NAME=?, DESCRIPTION=?, RELEASE_DATE=?, DURATION=? WHERE FILM_ID=?";
            jdbcTemplate.update(sql, film.getName(),
                                     film.getDescription(),
                                     film.getReleaseDate(),
                                     film.getDuration(),
                                     film.getId());
       return film;
    }

    @Override
    public Film getFilm(Long id) {
        testId (id);
        String sql = "SELECT * FROM FILMS WHERE FILM_ID = ?";
        SqlRowSet userRows = jdbcTemplate.queryForRowSet(sql, id);
        if (userRows.next()) {
            return  jdbcTemplate.queryForObject(sql, new FilmMapper(), id);
        } else {
            throw new NotFoundException("Такого фильма не существует");
        }
    }

    @Override
    public void deleteFilm(Long id) {
        testId (id);
        String sql = "DELETE FROM FILMS WHERE FILM_ID = ?";
        SqlRowSet userRows = jdbcTemplate.queryForRowSet(sql, id);
        if (userRows.next()) {
            jdbcTemplate.update(sql, id);
        } else {
            throw new NotFoundException("Такого фильма не существует");
        }
    }

    private int getRate (Film film){
        String sql = "SELECT * FROM FILM_RATE WHERE RATE_ID IN(SELECT RATE_ID FROM FILMS WHERE FILM_ID = ?)";
        Rate rate = jdbcTemplate.queryForObject(sql, new RateMapper(), film.getId());
        return rate.getRateId();
    }

    private List<Genre> getGenre (Film film) {
        String sql = "SELECT * FROM FILM_GENRE WHERE GENRE_ID IN (SELECT GENRE_ID FROM FILMS WHERE FILM_ID = ?)";
        List<Genre> genres = jdbcTemplate.query(sql, new GenreMapper(), film.getId());
        return genres;
    }



    private void testId (long id) {
            String sql = "SELECT * FROM FILMS WHERE FILM_ID = ?";
            Film film = jdbcTemplate.query(
                            sql,
                            new FilmMapper(),
                            id)
                    .stream()
                    .findAny()
                    .orElseThrow(() -> new NotFoundException("Фильма с id " + id + " нет в БД."));
                }
}

