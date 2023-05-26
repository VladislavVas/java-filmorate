package ru.yandex.practicum.filmorate.controller.dal.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.controller.model.Friends;

import java.sql.ResultSet;
import java.sql.SQLException;


public class FriendsMapper implements RowMapper<Friends> {

    @Override
    public Friends mapRow(ResultSet rs, int rowNum) throws SQLException {
        Friends friends = Friends.builder()
                .userId(rs.getLong(1))
                .friendId(rs.getLong(2))
                .status(rs.getBoolean(3))
                .build();
        return friends;
    }
}
