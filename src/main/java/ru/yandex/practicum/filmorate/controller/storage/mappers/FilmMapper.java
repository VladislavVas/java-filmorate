package ru.yandex.practicum.filmorate.controller.storage.mappers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.controller.model.Film;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class FilmMapper implements RowMapper<Film> {
    @Override
    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
        Film film = Film.builder()
                .id(rs.getLong(1))
                .name(rs.getString(2))
                .description(rs.getString(3))
                .releaseDate(rs.getDate(4).toLocalDate())
                .duration(rs.getLong(5))
                .rate(rs.getInt(6))
                .build();
        log.info("Выгрузка фильма из БД id= " + film.getId() + ".");
        return film;
    }
}
