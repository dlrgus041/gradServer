package org.grad.project.system;

import org.grad.project.model.Entry;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.Optional;

public interface Repository {

    void save(Entry entry);

    Optional<Entry> findById(int id);

    Optional<Entry> findByPhone(String phone);

    List<Entry> searchByName(String name);

    List<Entry> searchByPhone(String phone);

    List<Entry> searchByAddress(String address);

    List<Entry> findAll();

    boolean deleteById(int id);

    boolean updateById(int id, String name, String phone, String address);

    default RowMapper<Entry> rowMapper() {
        return (rs, rowNum) -> {
            Entry entry = new Entry();
            entry.setId(rs.getInt("id"));
            entry.setName(rs.getString("name"));
            entry.setPhone(rs.getString("phone"));
            entry.setAddress(rs.getString("address"));
            entry.setCode(Table.addressToCode(entry.getAddress()));
            return entry;
        };
    }
}
