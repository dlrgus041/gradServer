package org.grad.project.visitor;

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

    public void join(Visitor visitor) {
        visitorRepository.save(visitor);
    }

    public boolean isValid(String phone) {
        return visitorRepository.findByPhone(phone).isPresent();
    }

    public List<Visitor> findVisitors() {
        return visitorRepository.findAll();
    }

    public Optional<Visitor> findOne(Long memberId) {
        return visitorRepository.findById(memberId);
    }

    public List<Visitor> search(String domain, String value) {
        if (domain.equals("name")) return visitorRepository.searchByName(value);
        if (domain.equals("phone")) return visitorRepository.searchByPhone(value);
        if (domain.equals("address")) return visitorRepository.searchByAddress(value);
        return null;
    }

    public boolean deleteVisitors() {
        return visitorRepository.deleteAll();
    }

    public boolean deleteOne(Long visitorId) {
        return visitorRepository.deleteById(visitorId);
    }

    public boolean update(Visitor visitor) {
        return visitorRepository.updateById(visitor.getId(), visitor.getName(), visitor.getPhone(), visitor.getAddress());
    }
}
