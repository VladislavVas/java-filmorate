DROP TABLE film_mpa;
DROP TABLE mpa;
DROP TABLE film_genre;
DROP TABLE genre;
DROP TABLE likes;
DROP TABLE friendship;
DROP TABLE films;
DROP TABLE users;

CREATE TABLE IF NOT EXISTS films
(
    film_id      INTEGER AUTO_INCREMENT,
    film_name    CHARACTER VARYING(50) NOT NULL,
    description  CHARACTER VARYING(200),
    release_date DATE                  NOT NULL,
    duration     INTEGER,
    rate         INTEGER,
    CONSTRAINT films_pk
        PRIMARY KEY (film_id)
);

CREATE TABLE IF NOT EXISTS genre
(
    genre_id   INTEGER AUTO_INCREMENT
        CONSTRAINT genre_pk
            UNIQUE,
    genre_name CHARACTER VARYING(20) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS mpa
(
    mpa_id   INTEGER AUTO_INCREMENT,
    mpa_name CHARACTER VARYING(10),
    CONSTRAINT rate_pk
        PRIMARY KEY (mpa_id)
);

CREATE TABLE IF NOT EXISTS film_genre
(
    film_id  INTEGER,
    genre_id INTEGER,
    CONSTRAINT film_genre_films_film_id_fk
        FOREIGN KEY (film_id) REFERENCES films,
    CONSTRAINT film_genre_genre_genre_id_fk
        FOREIGN KEY (genre_id) REFERENCES genre (genre_id)
);

CREATE TABLE IF NOT EXISTS film_mpa
(
    film_id INTEGER,
    mpa_id  INTEGER auto_increment,
    CONSTRAINT film_rate_films_film_id_fk
        FOREIGN KEY (film_id) REFERENCES films,
    CONSTRAINT film_rate_rate_rate_id_fk
        FOREIGN KEY (mpa_id) REFERENCES mpa
);


CREATE TABLE IF NOT EXISTS users
(
    user_id  INTEGER AUTO_INCREMENT,
    email    CHARACTER VARYING(30) NOT NULL,
    login    CHARACTER VARYING(20) NOT NULL,
    name     CHARACTER VARYING(50),
    birthday DATE                  NOT NULL,
    CONSTRAINT users_pk
        PRIMARY KEY (user_id)
);

CREATE TABLE IF NOT EXISTS friendship
(
    user_id   INTEGER NOT NULL,
    friend_id INTEGER NOT NULL,
    status    BOOLEAN DEFAULT FALSE,
    CONSTRAINT friendship_users_user_id_fk
        FOREIGN KEY (friend_id) REFERENCES users,
    CONSTRAINT friendship_users_user_id_user_id_fk
        FOREIGN KEY (user_id) REFERENCES users
);

CREATE TABLE IF NOT EXISTS likes
(
    film_id INTEGER NOT NULL
        REFERENCES films
        REFERENCES films,
    user_id INTEGER NOT NULL
        REFERENCES users
        REFERENCES users,
    PRIMARY KEY (film_id, user_id)
);

MERGE INTO mpa KEY (mpa_id)
    VALUES (1, 'G'),
           (2, 'PG'),
           (3, 'PG-13'),
           (4, 'R'),
           (5, 'NC-17');

MERGE INTO genre KEY (genre_id)
    VALUES (1, 'Комедия'),
           (2, 'Драма'),
           (3, 'Мультфильм'),
           (4, 'Ужасы'),
           (5, 'Триллер'),
           (6, 'Детектив');