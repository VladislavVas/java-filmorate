package ru.yandex.practicum.filmorate.controller.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@Builder
public class Film implements Comparable<Film> {

    private Long id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Long duration;
    private List<Genres> genres;
    private Mpa mpa;
    private int rate;
    private Set<Long> likes;

    @Override
    public int compareTo(Film film) {
        return Integer.compare(film.likes.size(), this.likes.size());
    }

}