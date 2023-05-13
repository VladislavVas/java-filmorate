package ru.yandex.practicum.filmorate.controller.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Positive;

@Data
@Builder
public class Mpa {

    @Positive
    private int id;

    private String name;

}
