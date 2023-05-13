package ru.yandex.practicum.filmorate.controller.dal.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.dal.dao.UserDao;
import ru.yandex.practicum.filmorate.controller.dal.mappers.UserMapper;
import ru.yandex.practicum.filmorate.controller.dal.util.Validator;
import ru.yandex.practicum.filmorate.controller.model.User;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;


@Component
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {

    private final JdbcTemplate jdbcTemplate;
    private final Validator validator;

    private static final String GET_USER = "SELECT * FROM USERS WHERE USER_ID = ?";
    private static final String CREATE_USER = "INSERT INTO USERS(EMAIL, LOGIN, NAME, BIRTHDAY) values (?, ?, ?, ?)";
    private static final String GET_ALL_USERS = "SELECT * FROM USERS";
    private static final String UPDATE_USER = "UPDATE USERS SET EMAIL=?, LOGIN=?, NAME=?, BIRTHDAY=? WHERE USER_ID=?";

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query(GET_ALL_USERS, new UserMapper());
    }

    @Override
    public User createUser(User user) {
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
    public User updateUser(User user) {
        jdbcTemplate.update(UPDATE_USER, user.getEmail(), user.getLogin(), user.getName(), user.getBirthday(), user.getId());
        return getUser(user.getId());
    }

    @Override
    public User getUser(Long id) {
        return jdbcTemplate.queryForObject(GET_USER, new UserMapper(), id);
    }

}
