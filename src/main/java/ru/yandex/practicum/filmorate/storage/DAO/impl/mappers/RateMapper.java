package ru.yandex.practicum.filmorate.storage.DAO.impl.mappers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.Rate;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class RateMapper implements RowMapper<Rate> {
    @Override
    public Rate mapRow(ResultSet rs, int rowNum) throws SQLException {
        Rate rate = Rate.builder()
                .rateId(rs.getInt(1))
                .rateName(rs.getString(2))
                .build();
        log.info("Выгрузка рейтинга из БД.");
        return rate;
    }
}
