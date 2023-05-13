package ru.yandex.practicum.filmorate.controller.dal.util;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.exeption.ValidationException;
import ru.yandex.practicum.filmorate.controller.model.Film;
import ru.yandex.practicum.filmorate.controller.model.User;

import java.time.LocalDate;

@Component
public class Validator {

    public void userValidator(User user) {
        if (user.getName().isBlank() || user.getName() == null) {
            user.setName(user.getLogin());
        }
    }

    public void filmValidator(Film film) throws ValidationException {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28)))
            throw new ValidationException("The release date cannot be earlier than December 28, 1895.");
    }

}
