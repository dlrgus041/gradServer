package org.grad.project.visitor;

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
public class VisitorRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public VisitorRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Visitor save(Visitor visitor) {

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("grad.visitor");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", encrypt());
        parameters.put("name", visitor.getName());
        parameters.put("phone", visitor.getPhone());
        parameters.put("address", visitor.getAddress());

        jdbcInsert.execute(new MapSqlParameterSource(parameters));
        return visitor;
    }

    public Optional<Visitor> findById(Long id) {
        return jdbcTemplate.query("select * from grad.visitor where id = ?", visitorRowMapper(), id)
                .stream().findAny();
    }

    public Optional<Visitor> findByName(String name) {
        return jdbcTemplate.query("select * from grad.visitor where name = ?", visitorRowMapper(), name)
                .stream().findAny();
    }

    public Optional<Visitor> findByPhone(String phone) {
        return jdbcTemplate.query("select * from grad.visitor where phone = ?", visitorRowMapper(), phone)
                .stream().findAny();
    }

    public Optional<Visitor> findByAddress(String address) {
        return jdbcTemplate.query("select * from grad.visitor where address = ?", visitorRowMapper(), address)
                .stream().findAny();
    }

    public List<Visitor> searchByName(String name) {
        return jdbcTemplate.query("select * from grad.visitor where name like ?", visitorRowMapper(), "%" + name + "%");
    }

    public List<Visitor> searchByPhone(String phone) {
        return jdbcTemplate.query("select * from grad.visitor where phone like ?", visitorRowMapper(), "%" + phone + "%");
    }

    public List<Visitor> searchByAddress(String address) {
        return jdbcTemplate.query("select * from grad.visitor where address like ?", visitorRowMapper(), "%" + address + "%");
    }

    public List<Visitor> findAll() {
        return jdbcTemplate.query("select * from grad.visitor", visitorRowMapper());
    }

    public boolean deleteById(Long id) {
        jdbcTemplate.update("delete from grad.visitor where id = ?", id);
        return findById(id).isPresent();
    }

    public boolean deleteByName(String name) {
        jdbcTemplate.update("delete from grad.visitor where name = ?", name);
        return findByName(name).isPresent();
    }

    public boolean deleteAll() {
        jdbcTemplate.update("delete from grad.visitor");
        return findAll().isEmpty();
    }

    public boolean updateById(Long id, String name, String phone, String address) {
        if (findById(id).isEmpty()) return false;
        jdbcTemplate.update("update grad.visitor set name = ?, phone = ?, address = ? where id = ?", name, phone, address, id);
        return true;
    }

    private RowMapper<Visitor> visitorRowMapper() {
        return (rs, rowNum) -> {
            Visitor visitor = new Visitor();
            visitor.setId(rs.getLong("id"));
            visitor.setName(rs.getString("name"));
            visitor.setPhone(rs.getString("phone"));
            visitor.setAddress(rs.getString("address"));
            return visitor;
        };
    }

    private Long encrypt() {

        int[] code = new int[7];
        int sum = code[1] = 2;

        for (int i = 2; i < 6; i++) {
            code[i] = (int)(Math.random() * 10);
            sum += i * code[i];
        }

        code[6] = sum % 7;

        long ret = 0L;
        for (int i = 6; i > 0; i--) {
            for (int j = i; j < 6; j++) code[i] *= 10;
            ret += code[i];
        }
        return ret;
    }
}
