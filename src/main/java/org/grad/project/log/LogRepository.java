package org.grad.project.log;

import org.grad.project.model.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class LogRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LogRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void save(Log log) {

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("log");
        Map<String, Object> parameters = new HashMap<>();

        parameters.put("id", log.getId());
        parameters.put("temp", log.getTemp());
        parameters.put("time", log.getTime());
        parameters.put("valid", log.getValid());
        parameters.put("exist", log.getExist());
        parameters.put("within", log.getWithin());

        jdbcInsert.execute(new MapSqlParameterSource(parameters));
    }

    public List<Log> findAll() {
        return jdbcTemplate.query("select * from log", rowMapper());
    }

    private RowMapper<Log> rowMapper() {
        return (rs, rowNum) -> {
            Log log = new Log();
            log.setId(rs.getInt("id"));
            log.setTemp(rs.getFloat("temp"));
            log.setTime(rs.getString("time"));
            log.setValid(rs.getBoolean("valid"));
            log.setExist(rs.getBoolean("exist"));
            log.setWithin(rs.getBoolean("within"));
            return log;
        };
    }
}
