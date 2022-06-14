package ru.yandex.practicum.filmorate.controller;


import ru.yandex.practicum.filmorate.exeption.ValidationException;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.Film;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private Map<Long, Film> films = new HashMap<>();
    private int id = 1;

    @GetMapping
    public Collection<Film> getAll() {
        return films.values();
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) throws ValidationException {
        if(film.getName().isBlank()||film.getName() == null){
            throw new ValidationException("Название не может быть пустым.");
        }
        if(films.containsKey(film.getName())){
            throw new ValidationException("Такой фильм уже есть.");
        }
        if(film.getDescription().length()>200){
            throw new ValidationException("Описание не может содержать более 200 символов.");
        }
        if(film.getDuration() <= 0){
            throw new ValidationException("Продолжительность фильма не может быть отрицательной.");
        }
        if(film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28)))
            throw new ValidationException("Невозможно добавить фильм с датой релиза ранее 28.12.1985 г.");
        film.setId((long) id);
        id++;
        films.put(film.getId(), film);
        log.debug("Добавлен фильм: {}", film);
        return film;
    }

    @PutMapping
    public Film put(@Valid @RequestBody Film film) throws ValidationException{
        if (films.containsKey(film.getId())) {
            log.debug("Обновлена информация о фильме: {}", film);
            films.replace(film.getId(), film);
            return film;
        } else {
            throw new ValidationException("Попытка обновить несуществующий фильм");
        }
    }
}
