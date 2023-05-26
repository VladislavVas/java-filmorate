package ru.yandex.practicum.filmorate.controller.dal.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.dal.dao.FilmDao;
import ru.yandex.practicum.filmorate.controller.dal.mappers.FilmMapper;
import ru.yandex.practicum.filmorate.controller.dal.mappers.GenreMapper;
import ru.yandex.practicum.filmorate.controller.dal.mappers.MpaMapper;
import ru.yandex.practicum.filmorate.controller.dal.util.Validator;
import ru.yandex.practicum.filmorate.controller.exeption.ValidationException;
import ru.yandex.practicum.filmorate.controller.model.Film;
import ru.yandex.practicum.filmorate.controller.model.Genres;
import ru.yandex.practicum.filmorate.controller.model.Mpa;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;


@Component
@RequiredArgsConstructor
public class FilmDaoImpl implements FilmDao {

    private final JdbcTemplate jdbcTemplate;

    private static final String GET = "SELECT * FROM FILMS WHERE FILM_ID = ?";
    private static final String GET_ALL = "SELECT * FROM FILMS";
    private static final String GET_POPULAR = "select * from FILMS order by RATE limit  ?";
    private static final String CREATE = "INSERT INTO FILMS(FILM_NAME, DESCRIPTION, RELEASE_DATE, DURATION, RATE) values (?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE FILMS SET FILM_NAME=?, DESCRIPTION=?, RELEASE_DATE=?, DURATION=? WHERE FILM_ID=?";


    private static long mapRowToLong(ResultSet resultSet, int rowNum) throws SQLException {
        return resultSet.getLong(1);
    }

    @Override
    public List<Film> getAll() {
        List<Film> films = jdbcTemplate.query(GET_ALL, new FilmMapper());
        for (Film film : films) {
            getMpa(film);
            getGenre(film);
        }
        return films;
    }

    @Override
    public List<Film> getPopular(Integer count) {
        return jdbcTemplate.query(GET_POPULAR, (rs, rowNum) -> get(rs.getLong(1)), count);
    }

    @Override
    public Film create(Film film) throws ValidationException {
        Validator.filmValidator(film);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(CREATE, new String[]{"film_id"});
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
    public Film update(Film film) throws ValidationException {
        Validator.filmValidator(film);
        jdbcTemplate.update(UPDATE,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getId());
        return get(film.getId());
    }

    @Override
    public Film get(Long id) {
        Film film = jdbcTemplate.queryForObject(GET, new FilmMapper(), id);
        getGenre(film);
        getMpa(film);
        setLikes(film);
        return film;
    }

    private void getMpa(Film film) {
        String sql = "SELECT\n" +
                "FR.MPA_ID,\n" +
                "R.MPA_NAME\n" +
                "FROM FILM_MPA AS FR\n" +
                "LEFT JOIN MPA AS R ON R.MPA_ID = FR.MPA_ID\n" +
                "WHERE FILM_ID = ?";
        Mpa mpa = jdbcTemplate.queryForObject(sql, new MpaMapper(), film.getId());
        film.setMpa(mpa);
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

    private void setLikes(Film film) {
        String sql = "SELECT COUNT(USER_ID)\n" +
                "FROM LIKES\n" +
                "WHERE FILM_ID = ?";
        List<Long> likes = jdbcTemplate.query(sql, FilmDaoImpl::mapRowToLong, film.getId());
        film.setLikes(new HashSet<>(likes));
    }

}

