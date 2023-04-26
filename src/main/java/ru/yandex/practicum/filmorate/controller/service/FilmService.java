package ru.yandex.practicum.filmorate.controller.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controller.exeption.ValidationException;
import ru.yandex.practicum.filmorate.controller.model.Film;
import ru.yandex.practicum.filmorate.controller.storage.films.FilmStorage;
import ru.yandex.practicum.filmorate.controller.storage.users.UserStorage;
import ru.yandex.practicum.filmorate.controller.model.User;
import ru.yandex.practicum.filmorate.controller.storage.genre.GenreDbStorage;
import ru.yandex.practicum.filmorate.controller.storage.mpa.MpaDbStorage;
import ru.yandex.practicum.filmorate.controller.storage.like.LikeDbStorage;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final GenreDbStorage genreDbStorage;
    private final MpaDbStorage mpaDbStorage;
    private final LikeDbStorage likeDbStorage;


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
