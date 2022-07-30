package ru.yandex.practicum.filmorate.controller.storage.DAO.impl.mappers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.controller.model.Friends;

import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class FriendsMapper implements RowMapper <Friends>{

    @Override
    public Friends mapRow(ResultSet rs, int rowNum) throws SQLException {
        Friends friends = Friends.builder()
                .userId(rs.getLong(1))
                .friendId(rs.getLong(2))
                .status(rs.getBoolean(3))
                .build();
        log.info("Выгрузка списка друзей из БД.");
        return friends;
    }
}
