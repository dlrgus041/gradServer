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

    default int encrypt(int first) {

        int[] code = new int[7];
        int sum = code[1] = first;

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
