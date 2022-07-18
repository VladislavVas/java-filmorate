package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import java.time.Duration;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
public class Film implements Comparable<Film>{
    @JsonProperty("jsonData.id")
    private Long id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Long duration;
    private List<Genre> genre;
    private Integer rate;

    private Set<Long> likes = new HashSet<>();

    @Override
    public int compareTo(Film film) {
        return Integer.compare(film.likes.size(), this.likes.size());
    }
}
