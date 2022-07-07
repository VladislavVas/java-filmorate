package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.exeption.ServerException;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {

    private Validator validator;
    private static Map<Long, User> users = new HashMap<>();
    private static long id = 1;

    @Autowired
    public InMemoryUserStorage(Validator validator) {
        this.validator = validator;
    }

    public Collection<User> getAll(){
        return users.values();
    }

    @Override
    public User create(@Valid @RequestBody User user) throws ValidationException {
        validator.userValidator(user);
        user.setId(id);
        id++;
        users.put(user.getId(), user);
        log.debug("Создан новый пользователь: {}", user);
        return user;
    }

    @Override
    public User updateUser(@Valid @RequestBody User user) throws ValidationException {
            validator.userValidator(user);
            if(users.containsKey(user.getId())) {
                users.put(user.getId(), user);
                log.debug("Данные пользователя изменены: {}", user);
            } else {
                log.debug("Запрос на изменение неизвестного пользователя{}", user);
                throw new NotFoundException("Такого пользователя не существует");
            }
        return user;
    }

    @Override
    public User getUser(Long id) {
        if (users.get(id) != null) {
            return users.get(id);
        } else {
            log.info("Запрос неизвестного пользователя{}", id);
            throw new NotFoundException("Такого пользователя не существует");
        }
    }
}
