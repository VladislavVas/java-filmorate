package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.DAO.impl.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.DAO.impl.UserDbStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmorateApplicationTest {

    @Autowired
    private FilmController filmController;
    @Autowired
    private FilmDbStorage filmDbStorage;
    @Autowired
    private UserController userController;
    @Autowired
    private UserDbStorage userDbStorage;

    private  Film film = Film.builder()
            .name("testFilm")
            .description("testDescription")
            .releaseDate(LocalDate.of(2022,1,1))
            .duration(100L)
            .rate(1)
            .build();

    private  Film film2 = Film.builder()
            .name("testeSecondFilm")
            .description("testSecondDescription")
            .releaseDate(LocalDate.of(2020,1,1))
            .duration(90L)
            .rate(2)
            .mpa(Mpa.builder().id(1).name("G").build())
            .build();

    private User user = User.builder()
            .name("testName")
            .login("testLogin")
            .email("test@mail.ru")
            .birthday(LocalDate.of(2020, 1,1))
            .build();

    private User user2 = User.builder()
            .name("testName2")
            .login("testLogin2")
            .email("2test@mail.ru")
            .birthday(LocalDate.of(2019, 1,1))
            .build();



    @Test
    public void testCreateFilm() throws ValidationException {
        filmController.create(film);
        assertEquals(1, filmDbStorage.getAll().size());
        assertEquals("testFilm", filmDbStorage.getFilm(1L).getName());
    }

    @Test
    public void testUpdateFilm() throws ValidationException {
        film.setName("updateFilm");
        filmController.put(film);
        assertEquals("updateFilm", film.getName());
    }

    @Test
    public void testGetAll() throws ValidationException{
        filmController.create(film2);
        assertEquals(2,filmController.getAll().size());
    }

    @Test
    public void testGetFilmById(){
        assertEquals(1, filmController.getFilmById(1l).getId());
    }

    @Test
    public void testAddLike(){
        filmController.addLike(1L,1L);
        Film filmAddLikeTest = filmController.getFilmById(1L);
        assertEquals(1, filmAddLikeTest.getLikes().size());
    }

    @Test
    public void testGetPopular(){
        assertEquals(1,filmController.getPopularFilms(1).size());
    }

    @Test
    public void testDeleteLike(){
        filmController.deleteLike(1L,1L);
        Film filmDeleteLikeTest = filmController.getFilmById(1L);
        assertEquals(0,filmDeleteLikeTest.getLikes().size());
    }

    @Test
    public void testCreateUser() throws ValidationException{
        userController.create(user);
        userController.create(user2);
        assertEquals(2, userDbStorage.getAll().size());
    }

    @Test
    public void testGetAllUsers(){
        assertEquals(2, userController.getAll().size());
    }

    @Test
    public void testUpdateUser() throws ValidationException {
        user.setName("updateName");
        userController.put(user);
        assertEquals("updateName", userController.getUser(1).getName());
    }

    @Test
    public void testAddUserFriend(){
        userController.addFriend(1L,2L);
        assertEquals(1, userController.getUser(1).getFriends().size());
        assertEquals(0,userController.getUser(2L).getFriends().size());
        userController.addFriend(2L,1L);
        assertEquals(1,userController.getUser(2L).getFriends().size());
    }

    @Test
    public void testGetUserFriends(){
        assertTrue(userController.getUser(1L).getFriends().contains(2));
    }

    @Test
    public void testGetCommonFriends() throws ValidationException{
        User user3 = User.builder()
                .name("user3")
                .login("login3")
                .email("user3@mail.ru")
                .birthday(LocalDate.of(2001,1,1)).build();
        userController.create(user3);
        userController.addFriend(3L,1L);
        userController.addFriend(1L,3L);
        assertEquals(1,userController.getCommonFriends(3L,2L).size());
    }
}