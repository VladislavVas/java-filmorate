package ru.yandex.practicum.filmorate.controller.dal.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.dal.dao.GenreDao;
import ru.yandex.practicum.filmorate.controller.dal.mappers.GenreMapper;
import ru.yandex.practicum.filmorate.controller.model.Film;
import ru.yandex.practicum.filmorate.controller.model.Genres;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class GenreDaoImpl implements GenreDao {

    private final JdbcTemplate jdbcTemplate;

    private final String GET_ALL = "SELECT * FROM GENRE";

    private final String GET = "SELECT * FROM GENRE WHERE GENRE_ID = ?";

    private final String DELETE_BY_ID = "DELETE FROM FILM_GENRE WHERE FILM_ID = ?";

    @Override
    public List<Genres> getAll() {
        return jdbcTemplate.query(GET_ALL, new GenreMapper());
    }

    @Override
    public Genres get(int id) {
        return jdbcTemplate.queryForObject(GET, new GenreMapper(), id);
    }

    public void setFilmGenre(Film film) {
        List<Genres> genresList = film.getGenres();
        cleanTable(film.getId());
        if (genresList != null) {
            for (Genres genres : genresList) {
                String sql = "INSERT INTO FILM_GENRE (FILM_ID, GENRE_ID) VALUES (?, ?)";
                jdbcTemplate.update(sql, film.getId(), genres.getId());
            }
        }
    }

    private void cleanTable(Long id) {
        jdbcTemplate.update(DELETE_BY_ID, id);
    }
}


