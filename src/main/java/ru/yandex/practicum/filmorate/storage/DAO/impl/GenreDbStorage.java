package ru.yandex.practicum.filmorate.storage.DAO.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Rate;
import ru.yandex.practicum.filmorate.storage.DAO.impl.mappers.GenreMapper;
import ru.yandex.practicum.filmorate.storage.DAO.impl.mappers.RateMapper;
import ru.yandex.practicum.filmorate.storage.GenreDAO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Component
public class GenreDbStorage implements GenreDAO {

    private JdbcTemplate jdbcTemplate;

    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Collection<Genre> getAll() {
        final String sql = "SELECT * FROM GENRE";
        List<Genre> genres = jdbcTemplate.query(sql, new GenreMapper());
        return genres;
    }

    @Override
    public Genre get(int id) throws NotFoundException{
        if(id <= 0) {
            throw  new NotFoundException("Нет такого жанра!");
        }
        final String sql = "SELECT * FROM GENRE WHERE GENRE_ID = ?";
        final List<Genre> genreList = jdbcTemplate.query(sql, new GenreMapper(), id);
        Genre genre = genreList.get(0);
        return genre;
    }

    public Film setFilmGenre (Film film){
        //cleanTable(film.getId());
        if (film.getGenre()==null){
            String sql = "INSERT INTO FILM_GENRE (FILM_ID, GENRE_ID) VALUES (?, ?)";
            jdbcTemplate.update(sql, film.getId(), null);
        }

        if (film.getGenre() != null){
        List<Genre> genres = new ArrayList<>(film.getGenre());
        for (Genre genre : genres) {
            String sql = "INSERT INTO FILM_GENRE (FILM_ID, GENRE_ID) VALUES (?, ?)";
            jdbcTemplate.update(sql, film.getId(), genre.getId());
        }
    }
        return film;
    }

   private void cleanTable(Long id){
       String sql = "DELETE FROM FILM_GENRE WHERE FILM_ID = ?";
       jdbcTemplate.update(sql, id);
    }
}


