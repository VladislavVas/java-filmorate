package ru.yandex.practicum.filmorate.controller.dal.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.dal.dao.GenreDao;
import ru.yandex.practicum.filmorate.controller.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.controller.model.Film;
import ru.yandex.practicum.filmorate.controller.model.Genres;
import ru.yandex.practicum.filmorate.controller.dal.mappers.GenreMapper;

import java.util.Collection;
import java.util.List;

@Slf4j
@Component
public class GenreDaoImpl implements GenreDao {

    private JdbcTemplate jdbcTemplate;

    public GenreDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Collection<Genres> getAll() {
        final String sql = "SELECT * FROM GENRE";
        List<Genres> genres = jdbcTemplate.query(sql, new GenreMapper());
        return genres;
    }

    @Override
    public Genres get(int id) throws NotFoundException {
        if (id <= 0) {
            throw new NotFoundException("Нет такого жанра!");
        }
        final String sql = "SELECT * FROM GENRE WHERE GENRE_ID = ?";
        final List<Genres> genresList = jdbcTemplate.query(sql, new GenreMapper(), id);
        Genres genres = genresList.get(0);
        return genres;
    }

    public void setFilmGenre(Film film) {
        List<Genres> genresList = film.getGenres();
        cleanTable(film.getId());
       // List<Genres> genresList = genresSet.stream().collect(Collectors.toList());
        if (genresList != null) {
            for (Genres genres : genresList) {
                String sql = "INSERT INTO FILM_GENRE (FILM_ID, GENRE_ID) VALUES (?, ?)";
                jdbcTemplate.update(sql, film.getId(), genres.getId());
            }
        }
    }

    private void cleanTable(Long id) {
        String sql = "DELETE FROM FILM_GENRE WHERE FILM_ID = ?";
        jdbcTemplate.update(sql, id);
    }
}


