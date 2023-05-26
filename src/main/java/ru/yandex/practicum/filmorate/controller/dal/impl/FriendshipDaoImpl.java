package ru.yandex.practicum.filmorate.controller.dal.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.dal.dao.FriendshipDao;
import ru.yandex.practicum.filmorate.controller.exeption.NotFoundException;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FriendshipDaoImpl implements FriendshipDao {

    private final JdbcTemplate jdbcTemplate;

    private static final String GET_FRIENDS_IDS = "SELECT friend_id FROM friendship WHERE user_id = ?";

    private static final String ADD_FRIEND = "INSERT INTO friendship (user_id, friend_id) VALUES (?, ?)";

    private static final String DELETE = "DELETE FROM friendship WHERE user_id = ? AND friend_id = ?";

    @Override
    public List<Long> getFriendsIds(Long id) {
        return jdbcTemplate.query(GET_FRIENDS_IDS, (rs, numRow) -> rs.getLong("friend_id"), id);
    }

    @Override
    public void addFriend(Long userId, Long friendId) {
        jdbcTemplate.update(ADD_FRIEND, userId, friendId);
    }

    @Override
    public void deleteFriend(Long userId, Long friendId) {
        int rows = jdbcTemplate.update(DELETE, userId, friendId);
        if (rows == 0) {
            throw new NotFoundException("Friendship not found.");
        }
    }

}

