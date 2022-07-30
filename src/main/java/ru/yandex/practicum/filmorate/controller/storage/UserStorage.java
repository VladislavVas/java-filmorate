package ru.yandex.practicum.filmorate.controller.storage;

import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.controller.exeption.ValidationException;
import ru.yandex.practicum.filmorate.controller.model.User;

import javax.validation.Valid;
import java.util.Collection;

public interface UserStorage {
    Collection<User> getAll();
    User create(@Valid @RequestBody User user) throws ValidationException;
    User updateUser(@Valid @RequestBody User user) throws ValidationException;
    User getUser(Long id);
}
