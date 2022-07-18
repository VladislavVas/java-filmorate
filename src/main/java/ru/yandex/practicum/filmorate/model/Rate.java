package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Rate {
    @JsonProperty("id")
    private int rateId;
    @JsonProperty("name")
    private String rateName;
}
