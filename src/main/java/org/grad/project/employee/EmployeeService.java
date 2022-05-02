package org.grad.project.employee;

import org.grad.project.model.Entry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public void join(Entry employee) {
        employeeRepository.save(employee);
    }

    public boolean isValidById(int id) {
        return employeeRepository.findById(id).isPresent();
    }

    public boolean isValidByPhone(String phone) {
        return employeeRepository.findByPhone(phone).isPresent();
    }

    public List<Entry> findEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Entry> findById(int memberId) {
        return employeeRepository.findById(memberId);
    }

    public List<Entry> search(String domain, String value) {
        if (domain.equals("name")) return employeeRepository.searchByName(value);
        if (domain.equals("phone")) return employeeRepository.searchByPhone(value);
        if (domain.equals("address")) return employeeRepository.searchByAddress(value);
        return null;
    }

    public boolean deleteOne(int employeeId) {
        return employeeRepository.deleteById(employeeId);
    }

    public boolean update(Entry employee) {
        return employeeRepository.updateById(employee.getId(), employee.getName(), employee.getPhone(), employee.getAddress());
    }
}