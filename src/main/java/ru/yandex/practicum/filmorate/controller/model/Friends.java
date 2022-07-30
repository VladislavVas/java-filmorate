package ru.yandex.practicum.filmorate.controller.model;


import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class Friends {
        private Long userId;
        private Long friendId;
        private boolean status;
}
