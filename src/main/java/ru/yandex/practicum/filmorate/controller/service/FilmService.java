package ru.yandex.practicum.filmorate.controller.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controller.dal.dao.FilmDao;
import ru.yandex.practicum.filmorate.controller.dal.impl.GenreDaoImpl;
import ru.yandex.practicum.filmorate.controller.dal.impl.LikeDaoImpl;
import ru.yandex.practicum.filmorate.controller.dal.impl.MpaDaoImpl;
import ru.yandex.practicum.filmorate.controller.exeption.ValidationException;
import ru.yandex.practicum.filmorate.controller.model.Film;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilmService {

    private final FilmDao films;
    private final GenreDaoImpl genres;
    private final MpaDaoImpl mpas;
    private final LikeDaoImpl likes;


    public List<Film> getAll() {
        return films.getAll();
    }

    public Film getFilmById(Long filmId) {
        return films.get(filmId);
    }

    public List<Film> getPopular(Integer count) {
        return films.getPopular(count);
    }

    public void addLike(Long userId, Long filmId) {
        likes.addLike(userId, filmId);
    }


    public void deleteLike(Long filmId, Long userId) {
        likes.deleteLike(filmId, userId);
    }

    public Film updateFilm(Film film) throws ValidationException {
        films.update(film);
        genres.saveGenre(film);
        mpas.saveMpa(film);
        return getFilmById(film.getId());
    }

    public Film createFilm(Film film) throws ValidationException {
        films.create(film);
        genres.saveGenre(film);
        mpas.saveMpa(film);
        return getFilmById(film.getId());
    }

}
