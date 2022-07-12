package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import java.time.Duration;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film implements Comparable<Film>{
    private Long id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Long duration;
    private Set<Long> likes = new HashSet<>();

    @Override
    public int compareTo(Film film) {
        return Integer.compare(film.likes.size(), this.likes.size());
    }
}
