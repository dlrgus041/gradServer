package org.grad.project.visitor;

import org.grad.project.model.Entry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class VisitorRepository implements org.grad.project.system.Repository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public VisitorRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(Entry visitor) {

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("grad.visitor");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", encrypt(2));
        parameters.put("name", visitor.getName());
        parameters.put("phone", visitor.getPhone());
        parameters.put("address", visitor.getAddress());

        jdbcInsert.execute(new MapSqlParameterSource(parameters));
    }

    @Override
    public Optional<Entry> findById(int id) {
        return jdbcTemplate.query("select * from grad.visitor where id = ?", rowMapper(), id)
                .stream().findAny();
    }

    @Override
    public Optional<Entry> findByPhone(String phone) {
        return jdbcTemplate.query("select * from grad.visitor where phone = ?", rowMapper(), phone)
                .stream().findAny();
    }

    @Override
    public List<Entry> searchByName(String name) {
        return jdbcTemplate.query("select * from grad.visitor where name like ?", rowMapper(), "%" + name + "%");
    }

    @Override
    public List<Entry> searchByPhone(String phone) {
        return jdbcTemplate.query("select * from grad.visitor where phone like ?", rowMapper(), "%" + phone + "%");
    }

    @Override
    public List<Entry> searchByAddress(String address) {
        return jdbcTemplate.query("select * from grad.visitor where address like ?", rowMapper(), "%" + address + "%");
    }

    @Override
    public List<Entry> findAll() {
        return jdbcTemplate.query("select * from grad.visitor", rowMapper());
    }

    @Override
    public boolean deleteById(int id) {
        jdbcTemplate.update("delete from grad.visitor where id = ?", id);
        return findById(id).isPresent();
    }

    @Override
    public boolean updateById(int id, String name, String phone, String address) {
        if (findById(id).isEmpty()) return false;
        jdbcTemplate.update("update grad.visitor set name = ?, phone = ?, address = ? where id = ?", name, phone, address, id);
        return true;
    }
}
