package ru.yandex.practicum.filmorate.controller.dal.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.dal.dao.FriendshipDao;

import java.util.Collection;
import java.util.List;


@Slf4j
@Component
public class FriendshipDaoImpl implements FriendshipDao {

    private final JdbcTemplate jdbcTemplate;

    public FriendshipDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Collection<Long> getFriendsIds(Long id) {
        final String sql = "SELECT FRIEND_ID FROM FRIENDSHIP WHERE USER_ID = ?";
        List<Long> friends_id = jdbcTemplate.query(sql, (rs, numRow) -> rs.getLong("FRIEND_ID"), id);
        return friends_id;
    }

    @Override
    public void addFriend(Long user_id, Long friend_id) {
        final String sql = "INSERT INTO FRIENDSHIP (USER_ID, FRIEND_ID) VALUES (?, ?)";
        jdbcTemplate.update(sql, user_id, friend_id);
    }

    @Override
    public void deleteFriend(Long user_id, Long friend_id) {
        final String sql = "DELETE FROM FRIENDSHIP WHERE USER_ID = ? AND FRIEND_ID = ?";
        jdbcTemplate.update(sql, user_id, friend_id);
    }
}

