package org.grad.project.system;

import org.grad.project.entry.Entry;
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
            Entry visitor = new Entry();
            visitor.setId(rs.getInt("id"));
            visitor.setName(rs.getString("name"));
            visitor.setPhone(rs.getString("phone"));
            visitor.setAddress(rs.getString("address"));
            visitor.setCode(Util.addressToCode(visitor.getAddress()));
            return visitor;
        };
    }
}
