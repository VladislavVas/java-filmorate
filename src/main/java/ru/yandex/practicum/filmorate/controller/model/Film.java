package ru.yandex.practicum.filmorate.controller.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@Builder
public class Film implements Comparable<Film> {

    private Long id;
    @NotEmpty
    private String name;
    @Size(max = 200)
    private String description;
    @NotNull
    private LocalDate releaseDate;
    @Positive
    private Long duration;
    private List<Genres> genres;
    @NotNull
    private Mpa mpa;
    private int rate;
    private Set<Long> likes;

    @Override
    public int compareTo(Film film) {
        return Integer.compare(film.likes.size(), this.likes.size());
    }

}