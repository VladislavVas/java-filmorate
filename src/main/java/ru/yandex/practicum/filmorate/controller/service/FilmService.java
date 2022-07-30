package ru.yandex.practicum.filmorate.controller.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controller.exeption.ValidationException;
import ru.yandex.practicum.filmorate.controller.model.Film;
import ru.yandex.practicum.filmorate.controller.storage.FilmStorage;
import ru.yandex.practicum.filmorate.controller.storage.UserStorage;
import ru.yandex.practicum.filmorate.controller.model.User;
import ru.yandex.practicum.filmorate.controller.storage.DAO.impl.FilmDbStorage;
import ru.yandex.practicum.filmorate.controller.storage.DAO.impl.GenreDbStorage;
import ru.yandex.practicum.filmorate.controller.storage.DAO.impl.MpaDbStorage;
import ru.yandex.practicum.filmorate.controller.storage.DAO.impl.UserDbStorage;
import ru.yandex.practicum.filmorate.controller.storage.DAO.impl.LikeDbStorage;

import java.util.*;

@Service
@Slf4j
public class FilmService {

    private FilmStorage filmStorage;
    private UserStorage userStorage;
    private GenreDbStorage genreDbStorage;
    private MpaDbStorage mpaDbStorage;
    private LikeDbStorage likeDbStorage;

    @Autowired
    public FilmService(FilmDbStorage filmStorage,
                       UserDbStorage userStorage,
                       GenreDbStorage genreDbStorage,
                       MpaDbStorage mpaDbStorage,
                       LikeDbStorage likeDbStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
        this.genreDbStorage = genreDbStorage;
        this.mpaDbStorage = mpaDbStorage;
        this.likeDbStorage = likeDbStorage;
    }

    public List<Film> getAll() {
        return filmStorage.getAll();
    }


   public void addLike (long userId, long filmId){
            likeDbStorage.addLike(userId, filmId);
            log.info("Фильм id " + filmId + " получил лайк" + "пользователя " + userId + ".");
   }


    public void deleteLike(long filmId, long userId) {
            Film film = filmStorage.getFilm(filmId);
            User user = userStorage.getUser(userId);
        likeDbStorage.deleteLike(userId, filmId);
    }


    public Film updateFilm(Film film) throws ValidationException {
        filmStorage.updateFilm(film);
        genreDbStorage.setFilmGenre(film);
        mpaDbStorage.setFilmMpa(film);
        return filmStorage.getFilm(film.getId());
    }

    public Film createFilm(Film film) throws ValidationException {
            filmStorage.create(film);
            genreDbStorage.setFilmGenre(film);
            mpaDbStorage.setFilmMpa(film);
            return filmStorage.getFilm(film.getId());
    }
}
