package ru.yandex.practicum.filmorate.controller.storage.DAO.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.controller.exeption.ValidationException;
import ru.yandex.practicum.filmorate.controller.storage.DAO.impl.mappers.FilmMapper;
import ru.yandex.practicum.filmorate.controller.storage.DAO.impl.mappers.GenreMapper;
import ru.yandex.practicum.filmorate.controller.storage.DAO.impl.mappers.MpaMapper;
import ru.yandex.practicum.filmorate.controller.model.Film;
import ru.yandex.practicum.filmorate.controller.model.Genres;
import ru.yandex.practicum.filmorate.controller.model.Mpa;
import ru.yandex.practicum.filmorate.controller.storage.FilmStorage;
import ru.yandex.practicum.filmorate.controller.storage.Util.Validator;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
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

    private static long mapRowToLong(ResultSet resultSet, int rowNum) throws SQLException {
        return resultSet.getLong(1);
    }

    @Override
    public List<Film> getAll() {
        String sql = "SELECT * FROM FILMS";
        List<Film> filmsFromDb = jdbcTemplate.query(sql, new FilmMapper());
        for (Film film : filmsFromDb) {
            getMpa(film);
            getGenre(film);
        }
        return filmsFromDb;
    }

    @Override
    public List<Film> getPopular(Integer count) {
        String sql = "select * from FILMS order by RATE limit  ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> getFilm(rs.getLong(1)), count);
    }

    @Override
    public Film create(Film film) throws ValidationException {
        validator.filmValidator(film);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        final String sql = "INSERT INTO FILMS(FILM_NAME, DESCRIPTION, RELEASE_DATE, DURATION, RATE) values (?, ?, ?, ?, ?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"film_id"});
            stmt.setString(1, film.getName());
            stmt.setString(2, film.getDescription());
            stmt.setDate(3, Date.valueOf(film.getReleaseDate()));
            stmt.setLong(4, film.getDuration());
            stmt.setInt(5, film.getRate());
            return stmt;
        }, keyHolder);
        film.setId(keyHolder.getKey().longValue());
        return film;
    }

    @Override
    public Film updateFilm(Film film) throws ValidationException {
        testId(film.getId());
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
        testId(id);
        String sql = "SELECT * FROM FILMS WHERE FILM_ID = ?";
        SqlRowSet userRows = jdbcTemplate.queryForRowSet(sql, id);
        if (userRows.next()) {
            Film film = jdbcTemplate.queryForObject(sql, new FilmMapper(), id);
            getGenre(film);
            getMpa(film);
            setLikes(film);
            return film;
        } else {
            throw new NotFoundException("Такого фильма не существует");
        }
    }

    @Override
    public void deleteFilm(Long id) {
        testId(id);
        String sql = "DELETE FROM FILMS WHERE FILM_ID = ?";
        SqlRowSet userRows = jdbcTemplate.queryForRowSet(sql, id);
        if (userRows.next()) {
            jdbcTemplate.update(sql, id);
        } else {
            throw new NotFoundException("Такого фильма не существует");
        }
    }

    private void getMpa(Film film) {
        String sql = "SELECT\n" +
                "FR.MPA_ID,\n" +
                "R.MPA_NAME\n" +
                "FROM FILM_MPA AS FR\n" +
                "LEFT JOIN MPA AS R ON R.MPA_ID = FR.MPA_ID\n" +
                "WHERE FILM_ID = ?";
        SqlRowSet mpaRows = jdbcTemplate.queryForRowSet(sql, film.getId());
        if (mpaRows.next()){
            Mpa mpa = jdbcTemplate.queryForObject(sql, new MpaMapper(), film.getId());
            film.setMpa(mpa);
        }
    }

    private void getGenre(Film film) {
        String sql = "SELECT DISTINCT\n" +
                "FG.GENRE_ID,\n" +
                "G.GENRE_NAME\n" +
                "FROM FILM_GENRE AS FG\n" +
                "LEFT JOIN GENRE AS G ON G.GENRE_ID = FG.GENRE_ID\n" +
                "WHERE FILM_ID = ?";
        List<Genres> genres = jdbcTemplate.query(sql, new GenreMapper(), film.getId());
        film.setGenres(genres);
    }

    private void testId(long id) {
        String sql = "SELECT * FROM FILMS WHERE FILM_ID = ?";
        Film film = jdbcTemplate.query(
                        sql,
                        new FilmMapper(),
                        id)
                .stream()
                .findAny()
                .orElseThrow(() -> new NotFoundException("Фильма с id " + id + " нет в БД."));
    }

    @Override
    public Film setLikes(Film film) {
        long filmId = film.getId();
        String sql = "SELECT COUNT(USER_ID)\n" +
                "FROM LIKES\n" +
                "WHERE FILM_ID = ?";
        List<Long> likes = jdbcTemplate.query(sql, FilmDbStorage::mapRowToLong, filmId);
        film.setLikes(new HashSet<>(likes));
        return film;
    }
}

