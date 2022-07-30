package ru.yandex.practicum.filmorate.controller.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controller.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.controller.storage.DAO.impl.UserDbStorage;
import ru.yandex.practicum.filmorate.controller.storage.UserStorage;
import ru.yandex.practicum.filmorate.controller.model.User;
import ru.yandex.practicum.filmorate.controller.storage.DAO.impl.FriendsDbStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    private final UserStorage userStorage;
    private final FriendsDbStorage friendsDbStorage;

    @Autowired
    public UserService(UserDbStorage userStorage, FriendsDbStorage friendsDbStorage) {
        this.userStorage = userStorage;
        this.friendsDbStorage = friendsDbStorage;
    }

    public void addFriend(Long id, Long friendId){
        if (userStorage.getUser(id) != null && userStorage.getUser(friendId)!= null){
            friendsDbStorage.addFriend(id, friendId);
            log.info("Пользователь " + id + " добавил в друзья id " + friendId + ".");
        } else {
            throw new NotFoundException(String.format("Пользователь не найден"));
        }
    }

    public void deleteFriend(Long id, Long friendId){
        if (userStorage.getUser(id) != null && userStorage.getUser(friendId) != null){
            friendsDbStorage.deleteFriend(id, friendId);
            log.info("Пользователь " + id + " удалил из друзей id " + friendId + ".");
        } else {
            throw new NotFoundException(String.format("Пользователь не найден"));
        }
    }


    public List<User> getAllUserFriends (Long id){ //готово
        if(userStorage.getUser(id) != null) {
            log.info("Запрос друзей пользователя id " + id + ".");
            return friendsDbStorage.getFriendsIds(id)
                    .stream()
                    .map(userStorage::getUser)
                    .collect(Collectors.toList());
        } else {
            log.info("Запрос друзей несуществующего пользователя.");
            throw new NotFoundException(String.format("Пользователь {} не найден", id));
        }
    }

    public List<User> getCommonFriends (Long firstId, Long otherId) { //должно так же работать
        if (userStorage.getUser(firstId)!= null && userStorage.getUser(otherId)!= null){
            log.info("Запрос общих друзей пользователей id " + firstId + " и id " +
                    otherId + ".");
                List<User> user = getAllUserFriends(firstId);
                List<User> otherUser = getAllUserFriends(otherId);
                return user.stream().filter(otherUser::contains).collect(Collectors.toList());
        } else {
            log.info("Запрос общих друзей несуществующего пользователя.");
            throw new NotFoundException(String.format("Пользователь не найден"));
        }
    }
}

