package ru.yandex.practicum.filmorate.controller.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.controller.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.controller.storage.genre.GenreDbStorage;
import ru.yandex.practicum.filmorate.controller.model.Genres;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/genres")
public class GenreController {

    @Autowired
    private final GenreDbStorage genreDbStorage;

    public GenreController(GenreDbStorage genreDbStorage) {
        this.genreDbStorage = genreDbStorage;
    }

    @GetMapping
    public Collection<Genres> getAll() {
        return genreDbStorage.getAll();
    }

    @GetMapping("/{id}")
    public Genres get(@PathVariable int id) {
        Genres genres = genreDbStorage.get(id);
        if (genres == null) throw new NotFoundException("Запрошен неизвестный жанр!");
        return genres;
    }
}

