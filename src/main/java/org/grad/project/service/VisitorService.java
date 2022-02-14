package org.grad.project.service;

import org.grad.project.domain.Visitor;
import org.grad.project.repository.VisitorRepository;
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

    public Long join(Visitor visitor) {
        validateDuplicateVisitor(visitor);
        visitorRepository.save(visitor);
        return visitor.getId();
    }

    private void validateDuplicateVisitor(Visitor visitor) {
        visitorRepository.findByName(visitor.getName())
                .ifPresent(m -> {throw new IllegalStateException("이미 존재하는 회원입니다.");});
    }

    public List<Visitor> findVisitors() {
        return visitorRepository.findAll();
    }

    public Optional<Visitor> findOne(Long memberId) {
        return visitorRepository.findById(memberId);
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
