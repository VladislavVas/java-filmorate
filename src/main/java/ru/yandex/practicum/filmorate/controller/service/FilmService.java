package ru.yandex.practicum.filmorate.controller.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controller.exeption.ValidationException;
import ru.yandex.practicum.filmorate.controller.model.Film;
import ru.yandex.practicum.filmorate.controller.dal.dao.FilmDao;
import ru.yandex.practicum.filmorate.controller.dal.dao.UserDao;
import ru.yandex.practicum.filmorate.controller.model.User;
import ru.yandex.practicum.filmorate.controller.dal.impl.GenreDaoImpl;
import ru.yandex.practicum.filmorate.controller.dal.impl.MpaDaoImpl;
import ru.yandex.practicum.filmorate.controller.dal.impl.LikeDaoImpl;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilmService {

    private final FilmDao filmDAO;
    private final UserDao userDAO;
    private final GenreDaoImpl genreDaoImpl;
    private final MpaDaoImpl mpaDaoImpl;
    private final LikeDaoImpl likeDaoImpl;


    public List<Film> getAll() {
        return filmDAO.getAll();
    }


   public void addLike (long userId, long filmId){
            likeDaoImpl.addLike(userId, filmId);
            log.info("Фильм id " + filmId + " получил лайк" + "пользователя " + userId + ".");
   }


    public void deleteLike(long filmId, long userId) {
            Film film = filmDAO.getFilm(filmId);
            User user = userDAO.getUser(userId);
        likeDaoImpl.deleteLike(userId, filmId);
    }


    public Film updateFilm(Film film) throws ValidationException {
        filmDAO.updateFilm(film);
        genreDaoImpl.setFilmGenre(film);
        mpaDaoImpl.setFilmMpa(film);
        return filmDAO.getFilm(film.getId());
    }

    public Film createFilm(Film film) throws ValidationException {
            filmDAO.create(film);
            genreDaoImpl.setFilmGenre(film);
            mpaDaoImpl.setFilmMpa(film);
            return filmDAO.getFilm(film.getId());
    }
}
