package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Rate;
import ru.yandex.practicum.filmorate.storage.DAO.impl.GenreDbStorage;
import ru.yandex.practicum.filmorate.storage.DAO.impl.RateDbStorage;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/genres")
public class GenreController {

    @Autowired
    GenreDbStorage genreDbStorage;

    public GenreController(GenreDbStorage genreDbStorage) {
        this.genreDbStorage = genreDbStorage;
    }

    @GetMapping
    public Collection<Genre> getAll() {
        return genreDbStorage.getAll();
    }

    @GetMapping("/{id}")
    public Genre get(@PathVariable int id) {
        Genre genre = genreDbStorage.get(id);
        if (genre == null) throw new NotFoundException("Запрошен неизвестный жанр!");
        return genre;
    }

}

