package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Rate;

import java.util.Collection;

public interface RateDAO {
        Collection<Rate> getAll();
        Rate get(int id);
}
