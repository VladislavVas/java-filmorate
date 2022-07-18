package ru.yandex.practicum.filmorate.storage;

import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

public interface UserStorage {
    Collection<User> getAll();
    User create(@Valid @RequestBody User user) throws ValidationException;
    User updateUser(@Valid @RequestBody User user) throws ValidationException;
    User getUser(Long id);
}
