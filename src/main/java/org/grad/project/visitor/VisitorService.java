package org.grad.project.visitor;

import org.grad.project.model.Entry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VisitorService {

    private final VisitorRepository visitorRepository;

    @Autowired
    public VisitorService(VisitorRepository visitorRepository) {
        this.visitorRepository = visitorRepository;
    }

    public void join(Entry visitor) {
        visitorRepository.save(visitor);
    }

    public boolean isValid(String phone) {
        return visitorRepository.findByPhone(phone).isPresent();
    }

    public List<Entry> findVisitors() {
        return visitorRepository.findAll();
    }

    public Optional<Entry> findOne(int memberId) {
        return visitorRepository.findById(memberId);
    }

    public List<Entry> search(String domain, String value) {
        if (domain.equals("name")) return visitorRepository.searchByName(value);
        if (domain.equals("phone")) return visitorRepository.searchByPhone(value);
        if (domain.equals("address")) return visitorRepository.searchByAddress(value);
        return null;
    }

    public boolean deleteOne(int visitorId) {
        return visitorRepository.deleteById(visitorId);
    }

    public boolean update(Entry visitor) {
        return visitorRepository.updateById(visitor.getId(), visitor.getName(), visitor.getPhone(), visitor.getAddress());
    }
}
