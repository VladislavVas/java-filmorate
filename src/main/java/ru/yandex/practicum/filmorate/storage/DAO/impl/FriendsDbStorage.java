package ru.yandex.practicum.filmorate.storage.DAO.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.storage.FriendshipDAO;

import java.util.Collection;
import java.util.List;


@Slf4j
@Component
public class FriendsDbStorage implements FriendshipDAO {

    private final JdbcTemplate jdbcTemplate;

    public FriendsDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Collection<Long> getFriendsIds(Long id) {
        final String sql = "SELECT FRIEND_ID FROM FRIENDSHIP WHERE USER_ID = ?";
        List<Long> test = jdbcTemplate.query(sql, (rs, numRow) -> rs.getLong("FRIEND_ID"), id);
        return test;
    }

    @Override
    public void addFriend(Long user_id, Long friend_id) {
        final String sql = "INSERT INTO friendship (USER_ID, FRIEND_ID) VALUES (?, ?)";
        jdbcTemplate.update(sql, user_id, friend_id);
    }

    @Override
    public void deleteFriend(Long user_id, Long friend_id) {
        final String sql = "DELETE FROM friendship WHERE USER_ID = ? AND FRIEND_ID = ?";
        jdbcTemplate.update(sql, user_id, friend_id);
    }
}

