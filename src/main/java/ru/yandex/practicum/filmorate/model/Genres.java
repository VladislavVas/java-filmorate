package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Data
@Builder
public class Genres {
    private Integer id;
    @JsonProperty("name")
    private String genreName;
}
