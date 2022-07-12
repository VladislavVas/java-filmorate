package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    private UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void addFriend(Long id, Long friendId){
        if (userStorage.getUser(id) != null && userStorage.getUser(friendId) != null){
            User user = userStorage.getUser(id);
            Set<Long> userFriends= user.getFriends();
            userFriends.add(friendId);

            User friend = userStorage.getUser(friendId);
            Set<Long> friedFriends= friend.getFriends();
            friedFriends.add(id);
            log.info("Пользователь " + id + " добавил в друзья id " + friendId + ".");
        } else {
            throw new NotFoundException(String.format("Пользователь не найден"));
        }
    }

    public void deleteFriend(Long id, Long friendId){
        if (userStorage.getUser(id) != null && userStorage.getUser(friendId) != null){
            User user = userStorage.getUser(id);
            Set<Long> userFriends= user.getFriends();
            userFriends.remove(friendId);

            User friend = userStorage.getUser(friendId);
            Set<Long> friedFriends= friend.getFriends();
            friedFriends.remove(id);
            log.info("Пользователь " + id + " удалил из друзей id " + friendId + ".");
        } else {
            throw new NotFoundException(String.format("Пользователь не найден"));
        }
    }


    public Collection<User> getAllUserFriends (Long id){
        if(userStorage.getUser(id) != null) {
            User user = userStorage.getUser(id);
            Set<Long> friendsId = user.getFriends();
            List<User> friends = new ArrayList<>();
            for (Long i : friendsId){
                friends.add(userStorage.getUser(i));
            }
            log.info("Запрос друзей пользователя id " + id + ".");
            return friends;
        } else {
            log.info("Запрос друзей несуществующего пользователя.");
            throw new NotFoundException(String.format("Пользователь {} не найден", id));
        }
    }

    public Collection<User> getCommonFriends (Long firstId, Long otherId) {
        if (userStorage.getUser(firstId) != null && userStorage.getUser(otherId) != null){
            User firstUser = userStorage.getUser(firstId);
            Set<Long> firstUserFriendsSet = firstUser.getFriends();
            List<User> userFriendList = new ArrayList<>();
            for (Long i: firstUserFriendsSet) {
                User user = userStorage.getUser(i);
                userFriendList.add(user);
            }

            User otherUser = userStorage.getUser(otherId);
            Set<Long> otherUserFriendsSet = otherUser.getFriends();
            List<User> otherUserFriendList = new ArrayList<>();
            for (Long i: otherUserFriendsSet) {
                User user = userStorage.getUser(i);
                otherUserFriendList.add(user);
            }

            List<User> commonFriends = userFriendList.stream()
                    .filter(otherUserFriendList::contains)
                    .collect(Collectors.toList());
            log.info("Запрос общих друзей пользователей id " + firstId + " и id " +
                    otherId + ".");

            return commonFriends;

        } else {
            log.info("Запрос общих друзей несуществующего пользователя.");
            throw new NotFoundException(String.format("Пользователь не найден"));
        }
    }
}
