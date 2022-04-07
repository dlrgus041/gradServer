package org.grad.project.entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntryService {

    private final EntryRepository entryRepository;

    @Autowired
    public EntryService(EntryRepository entryRepository) {
        this.entryRepository = entryRepository;
    }

    public List<Entry> findEmployees() {
        return entryRepository.findAll();
    }

    public List<Entry> search(String domain, String value) {
        if (domain.equals("name")) return entryRepository.searchByName(value);
        if (domain.equals("phone")) return entryRepository.searchByPhone(value);
        if (domain.equals("address")) return entryRepository.searchByAddress(value);
//        if (domain.equals("class")) return entryRepository.searchByClass(value);
        return null;
    }
}
