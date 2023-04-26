package ru.yandex.practicum.filmorate.controller.storage.users;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.controller.exeption.ValidationException;
import ru.yandex.practicum.filmorate.controller.model.User;
import ru.yandex.practicum.filmorate.controller.storage.mappers.UserMapper;
import ru.yandex.practicum.filmorate.controller.storage.util.Validator;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.Collection;


@Slf4j
@Component
@RequiredArgsConstructor
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;
    private final Validator validator;

    private static final String GET_USER = "SELECT * FROM USERS WHERE USER_ID = ?";
    private static final String CREATE_USER = "INSERT INTO USERS(EMAIL, LOGIN, NAME, BIRTHDAY) values (?, ?, ?, ?)";
    private static final String GET_ALL_USERS = "SELECT * FROM USERS";
    private static final String UPDATE_USER = "UPDATE USERS SET EMAIL=?, LOGIN=?, NAME=?, BIRTHDAY=? WHERE USER_ID=?";

    @Override
    public Collection<User> getAll() {
        log.info("DB: Get all users");
        return jdbcTemplate.query(GET_ALL_USERS, new UserMapper());
    }


    @Override
    public User createUser(User user) throws ValidationException {
        validator.userValidator(user);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(CREATE_USER, new String[]{"user_id"});
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getLogin());
            stmt.setString(3, user.getName());
            stmt.setDate(4, Date.valueOf(user.getBirthday()));
            return stmt;
        }, keyHolder);
        user.setId(keyHolder.getKey().longValue());
        return user;
    }

    @Override
    public User updateUser(User user) throws ValidationException, NotFoundException {
        validator.userValidator(user);
        testId(user.getId());
        jdbcTemplate.update(UPDATE_USER, user.getEmail(), user.getLogin(), user.getName(), user.getBirthday(), user.getId());
        log.info("DB: update user  id=" + user.getId());
        return jdbcTemplate.queryForObject(GET_USER, new UserMapper(), user.getId());
    }

    @Override
    public User getUser(Long id) throws NotFoundException {
        testId(id);
        log.info("DB: get user id=" + id);
        return jdbcTemplate.queryForObject(GET_USER, new UserMapper(), id);
    }

    private void testId(Long id) {
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
