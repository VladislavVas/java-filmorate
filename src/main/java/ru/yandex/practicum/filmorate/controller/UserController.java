package ru.yandex.practicum.filmorate.controller;

import ru.yandex.practicum.filmorate.exeption.ValidationException;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private Map<Long, User> users = new HashMap<>();
    private long id = 1;

    @GetMapping
    public Collection<User> getAll(){
        return users.values();
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) throws ValidationException{
        if(user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            throw new ValidationException("Ошибка ввода электронной почты");
        }
        if(users.containsKey(user.getEmail())) {
            throw new ValidationException("Пользователь с электронной почтой " +
                    user.getEmail() + " уже зарегистрирован.");
        }
        if(user.getBirthday().isAfter(LocalDate.now())){
            throw new ValidationException("Дата рождения не может быть в будущем");
        }
        if(user.getLogin().contains(" ")){
            throw new ValidationException("Логин не может содержать пробелы");
        }
        if (user.getName().isBlank()||user.getName() == null){
            user.setName(user.getLogin());
        }
        user.setId(id);
        id++;
        users.put(user.getId(), user);
        log.debug("Создан новый пользователь: {}", user);
        return user;
    }

    @PutMapping
    public User put(@Valid @RequestBody User user) throws ValidationException {
        if(users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            log.debug("Данные пользователя изменены: {}", user);
        } else {
            throw new ValidationException("Такого пользователя не существует");
        }
        return user;
    }
}
