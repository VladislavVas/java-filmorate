package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
import java.util.HashSet;
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
    private Set<Long> likes = new HashSet<>();

    @Override
    public int compareTo(Film film) {
        return Integer.compare(film.likes.size(), this.likes.size());
    }

    public boolean addLike (Long userId){ // boolean??
      return likes.add(userId);
    }
    public boolean removeLike (Long userId){
      return likes.remove(userId);
    }
}