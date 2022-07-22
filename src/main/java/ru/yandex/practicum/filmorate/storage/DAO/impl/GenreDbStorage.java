package ru.yandex.practicum.filmorate.storage.DAO.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genres;
import ru.yandex.practicum.filmorate.storage.DAO.impl.mappers.GenreMapper;
import ru.yandex.practicum.filmorate.storage.GenreDAO;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class GenreDbStorage implements GenreDAO {

    private JdbcTemplate jdbcTemplate;

    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
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
        List<Genres> genresSet = film.getGenres();
        cleanTable(film.getId());
        List<Genres> genresList = genresSet.stream().collect(Collectors.toList());
        for (Genres genres : genresList) {
            String sql = "INSERT INTO FILM_GENRE (FILM_ID, GENRE_ID) VALUES (?, ?)";
            jdbcTemplate.update(sql, film.getId(), genres.getId());
        }
    }

    private void cleanTable(Long id) {
        String sql = "DELETE FROM FILM_GENRE WHERE FILM_ID = ?";
        jdbcTemplate.update(sql, id);
    }
}


