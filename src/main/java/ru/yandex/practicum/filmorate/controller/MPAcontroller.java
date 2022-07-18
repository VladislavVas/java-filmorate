package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Rate;
import ru.yandex.practicum.filmorate.storage.DAO.impl.RateDbStorage;

import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/mpa")
public class MPAcontroller {

    @Autowired
    RateDbStorage rateDbStorage;

    public MPAcontroller(RateDbStorage rateDbStorage) {
        this.rateDbStorage = rateDbStorage;
    }

    @GetMapping
    public List<Rate> getAll() {
        return rateDbStorage.getAll();
    }

    @GetMapping("/{id}")
    public Rate get(@PathVariable int id) {
        return rateDbStorage.get(id);
    }

}