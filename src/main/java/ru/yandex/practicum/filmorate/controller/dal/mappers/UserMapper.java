package ru.yandex.practicum.filmorate.controller.dal.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.controller.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;


public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return User.builder()
                .id(rs.getLong(1))
                .email(rs.getString(2))
                .login(rs.getString(3))
                .name(rs.getString(4))
                .birthday(rs.getDate(5).toLocalDate()).build();
    }
}
