package ru.yandex.practicum.filmorate.storage.DAO.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.DAO.impl.mappers.FilmMapper;
import ru.yandex.practicum.filmorate.storage.LikeDAO;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LikeDbStorage implements LikeDAO {

    private final JdbcTemplate jdbcTemplate;

    public LikeDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public void addLike(long filmId, long userId) {
        String sql = "INSERT INTO LIKES (FILM_ID, USER_ID) VALUES(?,?) ";
        jdbcTemplate.update(sql, filmId, userId);
    }

    @Override
    public void deleteLike(long filmId, long userId) {
        String sql = "DELETE  FROM LIKES WHERE FILM_ID = ? AND USER_ID = ?";
        jdbcTemplate.update(sql, filmId, userId);
    }

    @Override
    public List<Film> getPopularFilms(long count) {
        String sql =
"SELECT LIKES.FILM_ID, FILM_NAME, DESCRIPTION, RELEASE_DATE, DURATION FROM FILMS RIGHT JOIN LIKES on FILMS.FILM_ID = LIKES.FILM_ID GROUP BY LIKES.FILM_ID ORDER BY COUNT(USER_ID) DESC";
        return jdbcTemplate.query(
                        sql,
                        new FilmMapper())
                .stream()
                .limit(count)
                .collect(Collectors.toList());
    }
}
