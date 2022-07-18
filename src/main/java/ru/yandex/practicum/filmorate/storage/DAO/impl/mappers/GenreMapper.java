package ru.yandex.practicum.filmorate.storage.DAO.impl.mappers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.Genre;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class GenreMapper implements RowMapper<Genre> {
    @Override
    public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
        Genre genre = Genre.builder()
                .id(rs.getInt(1))
                .genreName(rs.getString(2))
                .build();
        log.info("Выгрузка жанра из БД.");
        return genre;
    }
}
