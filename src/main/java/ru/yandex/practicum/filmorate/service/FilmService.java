package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.exeption.ServerException;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.DAO.impl.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.DAO.impl.GenreDbStorage;
import ru.yandex.practicum.filmorate.storage.DAO.impl.RateDbStorage;
import ru.yandex.practicum.filmorate.storage.DAO.impl.UserDbStorage;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.DAO.impl.LikeDbStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FilmService {

    private FilmStorage filmStorage;
    private UserStorage userStorage;
    private GenreDbStorage genreDbStorage;
    private RateDbStorage rateDbStorage;
    private LikeDbStorage likeDbStorage;

    @Autowired
    public FilmService(FilmDbStorage filmStorage,
                       UserDbStorage userStorage,
                       GenreDbStorage genreDbStorage,
                       RateDbStorage rateDbStorage,
                       LikeDbStorage likeDbStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
        this.genreDbStorage = genreDbStorage;
        this.rateDbStorage = rateDbStorage;
        this.likeDbStorage = likeDbStorage;
    }

    public List<Film> getAll() {
        return filmStorage.getAll();
    }


   public void addLike (Long userId, Long filmId){
        likeDbStorage.addLike(userId, filmId);
        log.info("Фильм id " + filmId + " получил лайк" + "пользователя " + userId + ".");

   }


    public void deleteLike(Long filmId, Long userId) {
        likeDbStorage.deleteLike(userId, filmId);
    }

    public Collection<Film> getPopularFilms(Integer count) {
        return likeDbStorage.getPopularFilms(count);
    }

    public Film updateFilm(Film film) throws ValidationException {
        filmStorage.updateFilm(film);
        genreDbStorage.setFilmGenre(film);
        rateDbStorage.setFilmRate(film);
        return film;
    }

    public Film createFilm(Film film) throws ValidationException{
        if (film.getRate() != null){
            filmStorage.create(film);
            genreDbStorage.setFilmGenre(film);
            rateDbStorage.setFilmRate(film);
            return film;
        } else {
            throw new ServerException("Невозможно обработать фильм с пустым рейтингом");
        }

    }
}
