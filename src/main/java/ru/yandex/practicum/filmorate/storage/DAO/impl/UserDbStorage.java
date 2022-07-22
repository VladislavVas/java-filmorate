package ru.yandex.practicum.filmorate.storage.DAO.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.DAO.impl.mappers.UserMapper;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.storage.Util.Validator;

import java.util.Collection;


@Slf4j
@Component
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;
    private final Validator validator;


    @Autowired
    public UserDbStorage(JdbcTemplate jdbcTemplate, Validator validator) {
        this.jdbcTemplate = jdbcTemplate;
        this.validator = validator;
    }

    @Override
    public Collection<User> getAll() {
        String sql = "SELECT * FROM USERS";
        log.info("Запрос всех пользователей из БД.");
        return jdbcTemplate.query(sql, new UserMapper());
    }


    @Override
    public User create(User user) throws ValidationException { //может почту проверять?
        validator.userValidator(user);
        String sql = "INSERT INTO USERS(EMAIL, LOGIN, NAME, BIRTHDAY) values (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday());
        log.info("Создан новый пользователь.");
        String sqlForReturn = "SELECT * FROM USERS WHERE LOGIN = ?";
        return jdbcTemplate.query(sqlForReturn,
                        new UserMapper(),
                        user.getLogin())
                .stream()
                .findAny()
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
    }

    @Override
    public User updateUser(User user) throws ValidationException, NotFoundException {
        validator.userValidator(user);
        testId(user.getId());
        String sql = "UPDATE USERS SET EMAIL=?, LOGIN=?, NAME=?, BIRTHDAY=? WHERE USER_ID=?";
        jdbcTemplate.update(sql, user.getEmail(), user.getLogin(), user.getName(), user.getBirthday(), user.getId());
        log.info("Обновлены данные пользователя id " + user.getId() + ".");
        return user;
    }

    @Override
    public User getUser(Long id) throws NotFoundException {
        testId(id);
        String sql = "SELECT * FROM USERS WHERE USER_ID = ?";
        User user = jdbcTemplate.queryForObject(sql, new UserMapper(), id);
        return user;
    }

    private void testId(long id) {
        String sql = "SELECT * FROM USERS WHERE USER_ID = ?";
        User user = jdbcTemplate.query(
                        sql,
                        new UserMapper(),
                        id)
                .stream()
                .findAny()
                .orElseThrow(() -> new NotFoundException("Юзера с id " + id + " нет в БД."));
    }

}
