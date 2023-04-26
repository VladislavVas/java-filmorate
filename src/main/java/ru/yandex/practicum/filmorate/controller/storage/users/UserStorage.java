package ru.yandex.practicum.filmorate.controller.storage.users;

import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.controller.exeption.ValidationException;
import ru.yandex.practicum.filmorate.controller.model.User;

import javax.validation.Valid;
import java.util.Collection;

public interface UserStorage {
    Collection<User> getAll();
    User createUser(@Valid @RequestBody User user) throws ValidationException;
    User updateUser(@Valid @RequestBody User user) throws ValidationException;
    User getUser(Long id);
}
