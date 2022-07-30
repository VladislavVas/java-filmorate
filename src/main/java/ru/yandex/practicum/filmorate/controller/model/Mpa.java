package ru.yandex.practicum.filmorate.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

import javax.validation.constraints.Min;

@Data
@Builder
public class Mpa {
    @Min(1)
    private int id;
    private String name;
}
