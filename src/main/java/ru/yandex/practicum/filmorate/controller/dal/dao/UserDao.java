package ru.yandex.practicum.filmorate.controller.dal.dao;

import ru.yandex.practicum.filmorate.controller.exeption.ValidationException;
import ru.yandex.practicum.filmorate.controller.model.User;

import java.util.List;

public interface UserDao {

    List<User> getAll();

    User createUser(User user) throws ValidationException;

    User updateUser(User user) throws ValidationException;

    User getUser(Long id);

}
