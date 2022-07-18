package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.User;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.DAO.impl.UserDbStorage;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    UserDbStorage userStorage;
    UserService userService;

    @Autowired
    public UserController (UserDbStorage userDbStorage, UserService userService){
        this.userStorage = userDbStorage;
        this.userService = userService;
    }

    @GetMapping
    public Collection<User> getAll(){
        return userStorage.getAll();
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) throws ValidationException {
        return userStorage.create(user);
    }

    @PutMapping
    public User put(@Valid @RequestBody User user) throws ValidationException {
        return userStorage.updateUser(user);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable long id) {
        return userStorage.getUser(id);
    }

    @GetMapping("/{id}/friends")
     public List<User> getUserFriends(@PathVariable Long id) {
        return userService.getAllUserFriends(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable Long id,
                          @PathVariable Long friendId) {
       userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable Long id,
                             @PathVariable Long friendId) {
        userService.deleteFriend(id, friendId);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Collection<User> getCommonFriends(@PathVariable Long id,
                                             @PathVariable Long otherId) {
        return userService.getCommonFriends(id, otherId);
    }
}
