package ru.yandex.practicum.filmorate.controller.storage.like;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.storage.like.LikeDAO;

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
        setRate(filmId);
    }

    @Override
    public void deleteLike(long filmId, long userId) {
        String sql = "DELETE  FROM LIKES WHERE FILM_ID = ? AND USER_ID = ?";
        jdbcTemplate.update(sql, filmId, userId);
        setRate(filmId);
    }

    public void setRate(Long filmId) {
        String sql = "UPDATE FILMS AS F SET RATE = (SELECT COUNT(L.USER_ID)\n" +
                "FROM LIKES AS L WHERE L.FILM_ID = F.FILM_ID) WHERE FILM_ID = ?";
        jdbcTemplate.update(sql, filmId);
    }
}
