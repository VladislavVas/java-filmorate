package ru.yandex.practicum.filmorate.controller;


import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.Film;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.DAO.impl.FilmDbStorage;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private FilmDbStorage filmStorage;
    private FilmService filmService;

    @Autowired
    public FilmController(FilmDbStorage filmDbStorage, FilmService filmService){
        this.filmStorage = filmDbStorage;
        this.filmService = filmService;
    }

    @GetMapping
    public List<Film> getAll() {
        return filmService.getAll();
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) throws ValidationException {
        return filmService.createFilm(film);
    }

    @PutMapping
    public Film put(@Valid @RequestBody Film film) throws ValidationException{
        return filmService.updateFilm(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(
            @PathVariable Long id,
            @PathVariable Long userId) {
        getFilmById(id);
        filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(
            @PathVariable Long id,
            @PathVariable Long userId) {
        getFilmById(id);
        filmService.deleteLike(id, userId);
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable Long id) {
        return filmStorage.getFilm(id);
    }

    @GetMapping("/popular")
    public Collection<Film> getPopularFilms(
            @RequestParam(value = "count", defaultValue = "10", required = false) Integer count) {
        return filmService.getPopularFilms(count);
    }

    @DeleteMapping("/{id}")
    public void deleteFilmById(@RequestParam Long id){
        filmStorage.deleteFilm(id);
    }

}
