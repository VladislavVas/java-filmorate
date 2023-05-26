package ru.yandex.practicum.filmorate.controller.dal.dao;

import ru.yandex.practicum.filmorate.controller.exeption.ValidationException;
import ru.yandex.practicum.filmorate.controller.model.User;

import java.util.List;

public interface UserDao {

    List<User> getAll();

    User create(User user) throws ValidationException;

    User update(User user) throws ValidationException;

    User get(Long id);

}
