package ru.yandex.practicum.filmorate.controller.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequiredArgsConstructor
public class MpaController {

    private final MpaDaoImpl mpaDaoImpl;

    @GetMapping
    public ResponseEntity<List<Mpa>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(mpaDaoImpl.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mpa> get(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(mpaDaoImpl.get(id));
    }

}