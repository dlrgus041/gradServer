package org.grad.project.employee;

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
public class EmployeeRepository implements org.grad.project.system.Repository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public EmployeeRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(Entry employee) {

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("grad.employee");
        Map<String, Object> parameters = new HashMap<>();

        parameters.put("id", encrypt(1));
        parameters.put("name", employee.getName());
        parameters.put("phone", employee.getPhone());
        parameters.put("address", employee.getAddress());

        jdbcInsert.execute(new MapSqlParameterSource(parameters));
    }

    @Override
    public Optional<Entry> findById(int id) {
        return jdbcTemplate.query("select * from grad.employee where id = ?", rowMapper(), id)
                .stream().findAny();
    }

    @Override
    public Optional<Entry> findByPhone(String phone) {
        return jdbcTemplate.query("select * from grad.employee where phone = ?", rowMapper(), phone)
                .stream().findAny();
    }

    @Override
    public List<Entry> searchByName(String name) {
        return jdbcTemplate.query("select * from grad.employee where name like ?", rowMapper(), "%" + name + "%");
    }

    @Override
    public List<Entry> searchByPhone(String phone) {
        return jdbcTemplate.query("select * from grad.employee where phone like ?", rowMapper(), "%" + phone + "%");
    }

    @Override
    public List<Entry> searchByAddress(String address) {
        return jdbcTemplate.query("select * from grad.employee where address like ?", rowMapper(), "%" + address + "%");
    }

    @Override
    public List<Entry> findAll() {
        return jdbcTemplate.query("select * from grad.employee", rowMapper());
    }

    @Override
    public boolean deleteById(int id) {
        jdbcTemplate.update("delete from grad.employee where id = ?", id);
        return findById(id).isPresent();
    }

    @Override
    public boolean updateById(int id, String name, String phone, String address) {
        if (findById(id).isEmpty()) return false;
        jdbcTemplate.update("update grad.employee set name = ?, phone = ?, address = ? where id = ?", name, phone, address, id);
        return true;
    }
}
