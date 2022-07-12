package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FilmService {

    private FilmStorage filmStorage;
    private UserStorage userStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

   public Film addLike (Long userId, Long filmId){
        validateFilm(filmId);
        validateUser(userId);
        Film film = filmStorage.getFilm(filmId);
        Set<Long> likes = film.getLikes();
        likes.add(userId);
        log.info("Фильм id " + filmId + " получил лайк" + "пользователя " + userId + ".");
        return film;
   }


    public Film deleteLike(Long filmId, Long userId) {
        validateFilm(filmId);
        validateUser(userId);
        Film film = filmStorage.getFilm(filmId);
        Set<Long> likes = film.getLikes();
        likes.remove(userId);
        log.info("Пользователь " + userId + " удалил лайк у фильма id " + filmId + ".");
        return film;
    }

    public Collection<Film> getPopularFilms(Integer count) {
        return filmStorage.getAll().stream().sorted((p0, p1) -> {
            int compare = Integer.compare(p0.getLikes().size(), p1.getLikes().size());
            return compare;
        }).limit(count).collect(Collectors.toList());
    }
    private void validateUser(Long id) {
        if (userStorage.getUser(id)==null) {
            throw new NotFoundException(String.format("Не найден пользователь с id=%s", id));
        }
    }
    private void validateFilm(Long id) {
        if (filmStorage.getFilm(id) == null) {
            throw new NotFoundException(String.format("Не найден фильм с id=%s", id));
        }
    }
}
