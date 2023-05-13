package ru.yandex.practicum.filmorate.controller.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class User {

    @Positive
    private Long id;

    @Email
    private String email;

    @Pattern(regexp = "^[A-Za-z]*$")
    private String login;

    private String name;
    @Past
    private LocalDate birthday;

    private Set<Long> friends;

}
