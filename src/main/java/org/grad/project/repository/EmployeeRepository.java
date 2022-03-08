package org.grad.project.repository;

import org.grad.project.domain.Employee;
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
public class EmployeeRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public EmployeeRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void save(Employee employee) {

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("grad.employee");
        Map<String, Object> parameters = new HashMap<>();

        parameters.put("id", employee.getId());
        parameters.put("name", employee.getName());
        parameters.put("phone", employee.getPhone());
        parameters.put("address", employee.getAddress());
        parameters.put("vaccine", employee.getVaccine());

        jdbcInsert.execute(new MapSqlParameterSource(parameters));
    }

    public Optional<Employee> findById(Long id) {
        return jdbcTemplate.query("select * from grad.employee where id = ?", employeeRowMapper(), id)
                .stream().findAny();
    }

    public Optional<Employee> findByName(String name) {
        return jdbcTemplate.query("select * from grad.employee where name = ?", employeeRowMapper(), name)
                .stream().findAny();
    }

    public Optional<Employee> findByPhone(String phone) {
        return jdbcTemplate.query("select * from grad.employee where phone = ?", employeeRowMapper(), phone)
                .stream().findAny();
    }

    public Optional<Employee> findByAddress(String address) {
        return jdbcTemplate.query("select * from grad.employee where address = ?", employeeRowMapper(), address)
                .stream().findAny();
    }

    public Optional<Employee> findByVaccine(Boolean flag) {
        return jdbcTemplate.query("select * from grad.employee where vaccine = ?", employeeRowMapper(), flag)
                .stream().findAny();
    }

    public List<Employee> searchByName(String name) {
        return jdbcTemplate.query("select * from grad.employee where name like ?", employeeRowMapper(), "%" + name + "%");
    }

    public List<Employee> searchByPhone(String phone) {
        return jdbcTemplate.query("select * from grad.employee where phone like ?", employeeRowMapper(), "%" + phone + "%");
    }

    public List<Employee> searchByAddress(String address) {
        return jdbcTemplate.query("select * from grad.employee where address like ?", employeeRowMapper(), "%" + address + "%");
    }

    public List<Employee> findAll() {
        return jdbcTemplate.query("select * from grad.employee", employeeRowMapper());
    }

    public boolean deleteById(Long id) {
        jdbcTemplate.update("delete from grad.employee where id = ?", id);
        return findById(id).isPresent();
    }

    public boolean deleteByName(String name) {
        jdbcTemplate.update("delete from grad.employee where name = ?", name);
        return findByName(name).isPresent();
    }

    public boolean deleteAll() {
        jdbcTemplate.update("delete from grad.employee");
        return findAll().isEmpty();
    }

    public boolean updateById(Long id, String name, String phone, String address) {
        if (findById(id).isEmpty()) return false;
        jdbcTemplate.update("update grad.employee set name = ?, phone = ?, address = ? where id = ?", name, phone, address, id);
        return true;
    }

    private RowMapper<Employee> employeeRowMapper() {
        return (rs, rowNum) -> {
            Employee employee = new Employee();
            employee.setId(rs.getLong("id"));
            employee.setName(rs.getString("name"));
            employee.setPhone(rs.getString("phone"));
            employee.setAddress(rs.getString("address"));
            employee.setVaccine(rs.getInt("vaccine"));
            return employee;
        };
    }
}
