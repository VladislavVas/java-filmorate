package ru.yandex.practicum.filmorate.controller.dal.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.dal.dao.LikeDao;

@Component
@RequiredArgsConstructor
public class LikeDaoImpl implements LikeDao {

    private final JdbcTemplate jdbcTemplate;

    private static final String ADD_LIKE = "INSERT INTO LIKES (FILM_ID, USER_ID) VALUES(?,?)";

    private static final String DELETE_LIKE = "DELETE  FROM LIKES WHERE FILM_ID = ? AND USER_ID = ?";

    private static final String SET_RATE = "UPDATE FILMS AS F SET RATE = (SELECT COUNT(L.USER_ID)\n" +
            "FROM LIKES AS L WHERE L.FILM_ID = F.FILM_ID) WHERE FILM_ID = ?";

    @Override
    public void addLike(Long filmId, Long userId) {
        jdbcTemplate.update(ADD_LIKE, filmId, userId);
        setRate(filmId);
    }

    @Override
    public void deleteLike(Long filmId, Long userId) {
        jdbcTemplate.update(DELETE_LIKE, filmId, userId);
        setRate(filmId);
    }

    private void setRate(Long filmId) {
        jdbcTemplate.update(SET_RATE, filmId);
    }

}
