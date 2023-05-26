package ru.yandex.practicum.filmorate.controller.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.controller.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.controller.dal.impl.GenreDaoImpl;
import ru.yandex.practicum.filmorate.controller.model.Genres;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/genres")
public class GenreController {

    @Autowired
    private final GenreDaoImpl genreDaoImpl;

    public GenreController(GenreDaoImpl genreDaoImpl) {
        this.genreDaoImpl = genreDaoImpl;
    }

    @GetMapping
    public Collection<Genres> getAll() {
        return genreDaoImpl.getAll();
    }

    @GetMapping("/{id}")
    public Genres get(@PathVariable int id) {
        Genres genres = genreDaoImpl.get(id);
        if (genres == null) throw new NotFoundException("Запрошен неизвестный жанр!");
        return genres;
    }
}

