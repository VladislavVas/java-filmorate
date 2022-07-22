package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.time.LocalDate;
import java.util.*;

@Data
@Builder
public class Film implements Comparable<Film> {
  //  @JsonProperty("jsonData.id")
    private Long id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Long duration;
    private List<Genres> genres;
    private Mpa mpa;
    private int rate; //не реализованный фунционал, создан только для постман теста
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