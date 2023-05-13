package ru.yandex.practicum.filmorate.controller.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controller.dal.dao.FriendshipDao;
import ru.yandex.practicum.filmorate.controller.dal.dao.UserDao;
import ru.yandex.practicum.filmorate.controller.dal.util.Validator;
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
        validator.userValidator(user);
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
        users.getUser(id);
        users.getUser(friendId);
        friendship.addFriend(id, friendId);
    }

    public void deleteFriend(Long id, Long friendId) {
        friendship.deleteFriend(id, friendId);
    }

    public List<User> getAllUserFriends(Long id) {
        return friendship.getFriendsIds(id)
                .stream()
                .map(users::getUser)
                .collect(Collectors.toList());
    }

    public List<User> getCommonFriends(Long firstId, Long otherId) {
        List<User> user = getAllUserFriends(firstId);
        List<User> otherUser = getAllUserFriends(otherId);
        return user.stream().filter(otherUser::contains).collect(Collectors.toList());
    }

}

