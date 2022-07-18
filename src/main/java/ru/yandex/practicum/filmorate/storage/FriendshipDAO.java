package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Friends;

import java.util.Collection;

public interface FriendshipDAO {

    Collection<Long> getFriendsIds(Long id);

    void addFriend(Long user_id, Long friend_id);

    void deleteFriend(Long user_id, Long friend_id);
}
