package ru.yandex.practicum.filmorate.controller.storage.DAO.impl.mappers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.controller.model.Genres;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class GenreMapper implements RowMapper<Genres> {
    @Override
    public Genres mapRow(ResultSet rs, int rowNum) throws SQLException {
        Genres genres = Genres.builder()
                .id(rs.getInt(1))
                .genreName(rs.getString(2))
                .build();
        log.info("Выгрузка жанра из БД.");
        return genres;
    }
}
