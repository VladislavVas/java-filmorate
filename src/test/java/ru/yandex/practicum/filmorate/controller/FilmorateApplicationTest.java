package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.controller.controller.FilmController;
import ru.yandex.practicum.filmorate.controller.controller.UserController;
import ru.yandex.practicum.filmorate.controller.exeption.ValidationException;
import ru.yandex.practicum.filmorate.controller.model.Film;
import ru.yandex.practicum.filmorate.controller.model.Mpa;
import ru.yandex.practicum.filmorate.controller.model.User;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class FilmorateApplicationTest {

    @Autowired
    private FilmController filmController;
    @Autowired
    private UserController userController;


    private Film film = Film.builder()
            .name("testFilm")
            .description("testDescription")
            .releaseDate(LocalDate.of(2022,1,1))
            .duration(100L)
            .mpa(Mpa.builder().id(1).name("G").build())
            .rate(1)
            .build();

    private Film film2 = Film.builder()
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
        assertEquals(1, filmController.getAll().size());
        assertEquals("testFilm", filmController.getFilmById(1L).getName());
    }

    @Test
    public void testUpdateFilm() throws ValidationException {
        filmController.create(film);
        film.setName("updateFilm");
        filmController.put(film);
        assertEquals("updateFilm", film.getName());
    }

    @Test
    public void testGetAll() throws ValidationException{
        filmController.create(film);
        filmController.create(film2);
        assertEquals(2,filmController.getAll().size());
    }

    @Test
    public void testGetFilmById() throws ValidationException{
        filmController.create(film);
        assertEquals(1, filmController.getFilmById(1l).getId());
    }

    @Test
    public void testAddLike() throws ValidationException {
        filmController.create(film);
        userController.create(user);
        filmController.addLike(1L,1L);
        Film filmAddLikeTest = filmController.getFilmById(1L);
        assertEquals(1, filmAddLikeTest.getLikes().size());
    }

    @Test
    public void testGetPopular() throws ValidationException{
        filmController.create(film);
        filmController.create(film2);
        assertEquals(1,filmController.getPopularFilms(1).size());
    }

    @Test
    public void testDeleteLike() throws ValidationException {
        filmController.create(film);
        userController.create(user);
        filmController.addLike(1L,1L);
        filmController.deleteLike(1L,1L);
        film.getLikes();
        assertNull(film.getLikes());
    }

    @Test
    public void testCreateUser() throws ValidationException{
        userController.create(user);
        assertEquals(1, userController.getAll().size());
        assertEquals(1,userController.getUser(1).getId());
    }

    @Test
    public void testGetAllUsers() throws ValidationException {
        userController.create(user);
        userController.create(user2);
        assertEquals(2, userController.getAll().size());
    }

    @Test
    public void testUpdateUser() throws ValidationException {
        userController.create(user);
        user.setName("updateName");
        userController.put(user);
        assertEquals("updateName", userController.getUser(1).getName());
    }

    @Test
    public void testAddUserFriend() throws ValidationException{
        userController.create(user);
        userController.create(user2);
        userController.addFriend(1L,2L);
        userController.addFriend(2L,1L);
        assertEquals(1,userController.getUserFriends(1L).size());
    }

    @Test
    public void testGetUserFriends() throws ValidationException{
        userController.create(user);
        userController.create(user2);
        userController.addFriend(1L, 2L);
        assertEquals(1, userController.getUserFriends(1L).size());
    }

    @Test
    public void testGetCommonFriends() throws ValidationException{
        User user3 = User.builder()
                .name("user3")
                .login("login3")
                .email("user3@mail.ru")
                .birthday(LocalDate.of(2001,1,1)).build();
        userController.create(user);
        userController.create(user2);
        userController.create(user3);
        userController.addFriend(1L, 3L);
        userController.addFriend(2L,3L);
        assertEquals(1,userController.getCommonFriends(1L,2L).size());
    }
}