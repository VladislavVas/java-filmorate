package ru.yandex.practicum.filmorate.controller.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controller.dal.dao.FriendshipDao;
import ru.yandex.practicum.filmorate.controller.dal.dao.UserDao;
import ru.yandex.practicum.filmorate.controller.dal.util.Validator;
import ru.yandex.practicum.filmorate.controller.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.controller.exeption.ValidationException;
import ru.yandex.practicum.filmorate.controller.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserDao users;
    private final FriendshipDao friendship;
    private final Validator validator;

    public User createUser(User user) throws ValidationException {
        return users.createUser(user);
    }

    public User getUser(Long id) {
            return users.getUser(id);
    }

    public User updateUser(User user) throws ValidationException {
        validator.userValidator(user);
        return users.updateUser(user);
    }

    public List<User> getAllUsers() {
        return users.getAll();
    }

    public void addFriend(Long id, Long friendId) {
        friendship.addFriend(id, friendId);
        log.info("Пользователь " + id + " добавил в друзья id " + friendId + ".");

    }

    public void deleteFriend(Long id, Long friendId) {
        if (users.getUser(id) != null && users.getUser(friendId) != null) {
            friendship.deleteFriend(id, friendId);
            log.info("Пользователь " + id + " удалил из друзей id " + friendId + ".");
        } else {
            throw new NotFoundException("User not found");
        }
    }

    public List<User> getAllUserFriends(Long id) {
        if (users.getUser(id) != null) {
            log.info("Запрос друзей пользователя id " + id + ".");
            return friendship.getFriendsIds(id)
                    .stream()
                    .map(users::getUser)
                    .collect(Collectors.toList());
        } else {
            log.info("Запрос друзей несуществующего пользователя.");
            throw new NotFoundException("User not found");
        }
    }

    public List<User> getCommonFriends(Long firstId, Long otherId) {
        if (users.getUser(firstId) != null && users.getUser(otherId) != null) {
            log.info("Запрос общих друзей пользователей id " + firstId + " и id " +
                    otherId + ".");
            List<User> user = getAllUserFriends(firstId);
            List<User> otherUser = getAllUserFriends(otherId);
            return user.stream().filter(otherUser::contains).collect(Collectors.toList());
        } else {
            log.info("Запрос общих друзей несуществующего пользователя.");
            throw new NotFoundException("User not found");
        }
    }

}

