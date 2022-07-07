package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {

    Validator validator;
    private Map<Long, Film> films = new HashMap<>();
    private long id = 1;

    @Autowired
    public InMemoryFilmStorage (Validator validator){
        this.validator = validator;
    }

    public List<Film> getAll() {
        List<Film> filmsList = new ArrayList<>();
        for (Long i : films.keySet()) {
            filmsList.add(films.get(i));
        }
        return filmsList;
    }

    @Override
    public Film create(@Valid @RequestBody Film film) throws ValidationException {
        validator.filmValidator(film);
        film.setId((long) id);
        id++;
        films.put(film.getId(), film);
        log.debug("Добавлен фильм: {}", film);
        return film;
    }

    @Override
    public Film put(@Valid @RequestBody Film film) throws ValidationException{
        validator.filmValidator(film);
        if (films.containsKey(film.getId())) {
            log.debug("Обновлена информация о фильме: {}", film);
            films.replace(film.getId(), film);
            return film;
        } else {
            log.info("Запрос на обновление неизвестного фильма{}", id);
            throw new NotFoundException("Такого фильма не существует");
        }
    }

    @Override
    public Film getFilm (Long id) {
        if (films.get(id) != null) {
            return films.get(id);
        } else {
            log.info("Запрос неизвестного фильма{}", id);
            throw new NotFoundException("Такого фильма не существует");
        }
    }

    @Override
    public void deleteFilm(Long id){
        if (films.get(id) != null){
            films.remove(id);
            log.info("Удален фильм id {}", id);
        } else {
            log.info("Запрос на удаление неизвестного фильма{}", id);
            throw new NotFoundException("Такого фильма не существует");
        }
    }
}
