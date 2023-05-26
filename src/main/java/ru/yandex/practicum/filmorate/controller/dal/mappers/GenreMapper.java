package ru.yandex.practicum.filmorate.controller.dal.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.controller.model.Genres;

import java.sql.ResultSet;
import java.sql.SQLException;


public class GenreMapper implements RowMapper<Genres> {
    @Override
    public Genres mapRow(ResultSet rs, int rowNum) throws SQLException {
        Genres genres = Genres.builder()
                .id(rs.getInt(1))
                .genreName(rs.getString(2))
                .build();
        return genres;
    }
}
