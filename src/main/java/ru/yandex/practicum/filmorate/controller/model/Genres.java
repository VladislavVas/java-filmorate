package ru.yandex.practicum.filmorate.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Positive;

@Data
@Builder
public class Genres {

    @Positive
    private Integer id;

    @JsonProperty("name")
    private String genreName;

}
