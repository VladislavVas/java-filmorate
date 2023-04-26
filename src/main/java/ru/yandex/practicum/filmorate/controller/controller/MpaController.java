package ru.yandex.practicum.filmorate.controller.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.controller.dal.impl.MpaDaoImpl;
import ru.yandex.practicum.filmorate.controller.model.Mpa;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/mpa")
public class MpaController {

    @Autowired
    private final MpaDaoImpl mpaDaoImpl;

    public MpaController(MpaDaoImpl mpaDaoImpl) {
        this.mpaDaoImpl = mpaDaoImpl;
    }

    @GetMapping
    public List<Mpa> getAll() {
        return mpaDaoImpl.getAll();
    }

    @GetMapping("/{id}")
    public Mpa get(@PathVariable int id) {
        return mpaDaoImpl.get(id);
    }

}