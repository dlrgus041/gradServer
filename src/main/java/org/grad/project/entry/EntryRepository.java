package org.grad.project.entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class EntryRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public EntryRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Entry> searchByName(String name) {
        return jdbcTemplate.query("select * from grad.entry where name like ?", entryRowMapper(), "%" + name + "%");
    }

    public List<Entry> searchByPhone(String phone) {
        return jdbcTemplate.query("select * from grad.entry where phone like ?", entryRowMapper(), "%" + phone + "%");
    }

    public List<Entry> searchByAddress(String address) {
        return jdbcTemplate.query("select * from grad.entry where address like ?", entryRowMapper(), "%" + address + "%");
    }

    public List<Entry> searchByClass(boolean flag) {
        return jdbcTemplate.query("select * from grad.entry where mod(class, 2) = ?", entryRowMapper(), (flag ? 0 : 1));
    }

    public List<Entry> findAll() {
        return jdbcTemplate.query("select * from grad.entry", entryRowMapper());
    }

    private RowMapper<Entry> entryRowMapper() {
        return (rs, rowNum) -> {
            Entry entry = new Entry();
            entry.setId(rs.getLong("id"));
            entry.setName(rs.getString("name"));
            entry.setPhone(rs.getString("phone"));
            entry.setAddress(rs.getString("address"));
            return entry;
        };
    }
}
