drop table FILM_MPA;
drop table MPA;
drop table FILM_GENRE;
drop table GENRE;
drop table LIKES;
drop table FRIENDSHIP;
drop table FILMS;
drop table USERS;

create table if not exists FILMS
(
    FILM_ID      INTEGER auto_increment,
    FILM_NAME    CHARACTER VARYING(50) not null,
    DESCRIPTION  CHARACTER VARYING(200),
    RELEASE_DATE DATE                  not null,
    DURATION     INTEGER,
    RATE         INTEGER,
    constraint FILMS_PK
        primary key (FILM_ID)
);

create table if not exists GENRE
(
    GENRE_ID   INTEGER auto_increment
        constraint GENRE_PK
            unique,
    GENRE_NAME CHARACTER VARYING(20) default NULL
);

create table if not exists MPA
(
    MPA_ID   INTEGER auto_increment,
    MPA_NAME CHARACTER VARYING(10),
    constraint RATE_PK
        primary key (MPA_ID)
);

create table if not exists FILM_GENRE
(
    FILM_ID  INTEGER,
    GENRE_ID INTEGER,
    constraint FILM_GENRE_FILMS_FILM_ID_FK
        foreign key (FILM_ID) references FILMS,
    constraint FILM_GENRE_GENRE_GENRE_ID_FK
        foreign key (GENRE_ID) references GENRE (GENRE_ID)
);

create table if not exists FILM_MPA
(
    FILM_ID INTEGER,
    MPA_ID  INTEGER auto_increment,
    constraint FILM_RATE_FILMS_FILM_ID_FK
        foreign key (FILM_ID) references FILMS,
    constraint FILM_RATE_RATE_RATE_ID_FK
        foreign key (MPA_ID) references MPA
);


create table if not exists USERS
(
    USER_ID  INTEGER auto_increment,
    EMAIL    CHARACTER VARYING(30) not null,
    LOGIN    CHARACTER VARYING(20) not null,
    NAME     CHARACTER VARYING(50),
    BIRTHDAY DATE                  not null,
    constraint USERS_PK
        primary key (USER_ID)
);

create table if not exists FRIENDSHIP
(
    USER_ID   INTEGER not null,
    FRIEND_ID INTEGER not null,
    STATUS    BOOLEAN default FALSE,
    constraint FRIENDSHIP_USERS_USER_ID_FK
        foreign key (FRIEND_ID) references USERS,
    constraint FRIENDSHIP_USERS_USER_ID_USER_ID_FK
        foreign key (USER_ID) references USERS
);

create table if not exists LIKES
(
    FILM_ID INTEGER not null
        references FILMs
        references FILMs,
    USER_ID INTEGER not null
        references USERs
        references USERs,
    primary key (FILM_ID, USER_ID)
);

MERGE INTO MPA key (MPA_ID)
    VALUES (1, 'G'),
           (2, 'PG'),
           (3, 'PG-13'),
           (4, 'R'),
           (5, 'NC-17');

MERGE INTO GENRE key (GENRE_ID)
    VALUES (1, 'Комедия'),
           (2, 'Драма'),
           (3, 'Мультфильм'),
           (4, 'Ужасы'),
           (5, 'Триллер'),
           (6, 'Детектив');