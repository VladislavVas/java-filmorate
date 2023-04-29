package ru.yandex.practicum.filmorate.controller.dal.dao;

import java.util.List;

public interface FriendshipDao {

    List<Long> getFriendsIds(Long id);

    void addFriend(Long user_id, Long friend_id);

    void deleteFriend(Long user_id, Long friend_id);
}
