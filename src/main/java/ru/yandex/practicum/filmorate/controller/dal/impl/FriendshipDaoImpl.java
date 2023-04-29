package ru.yandex.practicum.filmorate.controller.dal.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.dal.dao.FriendshipDao;
import ru.yandex.practicum.filmorate.controller.exeption.NotFoundException;

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
    public void addFriend(Long user_id, Long friend_id) {
        try {
            jdbcTemplate.update(ADD_FRIEND, user_id, friend_id);
        } catch (Exception e) {
            throw new NotFoundException("User not found");
        }
    }

    @Override
    public void deleteFriend(Long user_id, Long friend_id) {
        jdbcTemplate.update(DELETE_FRIEND, user_id, friend_id);
    }

}

