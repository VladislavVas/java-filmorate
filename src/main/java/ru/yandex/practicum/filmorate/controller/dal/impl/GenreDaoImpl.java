package ru.yandex.practicum.filmorate.controller.dal.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.dal.dao.GenreDao;
import ru.yandex.practicum.filmorate.controller.dal.mappers.GenreMapper;
import ru.yandex.practicum.filmorate.controller.model.Film;
import ru.yandex.practicum.filmorate.controller.model.Genres;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GenreDaoImpl implements GenreDao {

    private final JdbcTemplate jdbcTemplate;

    private static final String GET_ALL = "SELECT * FROM genre";

    private static final String GET = "SELECT * FROM genre WHERE genre_id = ?";

    private static final String DELETE_BY_ID = "DELETE FROM film_genre WHERE film_id = ?";
    private static final String SAVE_GENRE = "INSERT INTO film_genre (film_id, genre_id) VALUES (?, ?)";

    @Override
    public List<Genres> getAll() {
        return jdbcTemplate.query(GET_ALL, new GenreMapper());
    }

    @Override
    public Genres get(int id) {
        return jdbcTemplate.queryForObject(GET, new GenreMapper(), id);
    }

    @Override
    public void saveGenre(Film film) {
        List<Genres> genresList = film.getGenres();
        cleanTable(film.getId());
        if (genresList != null) {
            for (Genres genres : genresList) {
                jdbcTemplate.update(SAVE_GENRE, film.getId(), genres.getId());
            }
        }
    }

    private void cleanTable(Long id) {
        jdbcTemplate.update(DELETE_BY_ID, id);
    }
}


