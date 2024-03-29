package ru.yandex.practicum.filmorate.controller.dal.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.controller.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;


public class MpaMapper implements RowMapper<Mpa> {
    @Override
    public Mpa mapRow(ResultSet rs, int rowNum) throws SQLException {
        Mpa mpa = Mpa.builder()
                .id(rs.getInt(1))
                .name(rs.getString(2))
                .build();
        return mpa;
    }
}
