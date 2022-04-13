package org.grad.project.visitor;

import org.grad.project.entry.Entry;
import org.grad.project.system.Util;
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
        parameters.put("id", encrypt());
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

    private int encrypt() {

        int[] code = new int[7];
        int sum = code[1] = 2;

        for (int i = 2; i < 6; i++) {
            code[i] = (int)(Math.random() * 10);
            sum += i * code[i];
        }

        code[6] = sum % 7;

        int ret = 0;
        for (int i = 6; i > 0; i--) {
            for (int j = i; j < 6; j++) code[i] *= 10;
            ret += code[i];
        }
        return ret;
    }
}
