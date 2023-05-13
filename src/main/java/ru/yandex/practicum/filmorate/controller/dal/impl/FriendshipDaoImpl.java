package ru.yandex.practicum.filmorate.controller.dal.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.dal.dao.FriendshipDao;

import java.util.List;


@Slf4j
@Component
@RequiredArgsConstructor
public class FriendshipDaoImpl implements FriendshipDao {

    private final JdbcTemplate jdbcTemplate;

    private final String GET_FRIENDS_IDS = "SELECT FRIEND_ID FROM FRIENDSHIP WHERE USER_ID = ?";

    private final String ADD_FRIEND = "INSERT INTO FRIENDSHIP (USER_ID, FRIEND_ID) VALUES (?, ?)";

    private final String DELETE_FRIEND = "DELETE FROM FRIENDSHIP WHERE USER_ID = ? AND FRIEND_ID = ?";

    @Override
    public List<Long> getFriendsIds(Long id) {
        return jdbcTemplate.query(GET_FRIENDS_IDS, (rs, numRow) -> rs.getLong("FRIEND_ID"), id);
    }

    @Override
    public void addFriend(Long userId, Long friendId) {
        jdbcTemplate.update(ADD_FRIEND, userId, friendId);
    }

    @Override
    public void deleteFriend(Long userId, Long friendId) {
        jdbcTemplate.update(DELETE_FRIEND, userId, friendId);
    }

}

