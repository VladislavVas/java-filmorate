package ru.yandex.practicum.filmorate.controller.dal.dao;

public interface LikeDao {

    void addLike(Long filmId, Long userId);

    void deleteLike(Long filmId, Long userId);

}
