package org.grad.project.employee;

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

    public void join(Employee employee) {
        employeeRepository.save(employee);
    }

    public boolean isValidById(Long id) {
        return employeeRepository.findById(id).isPresent();
    }

    public boolean isValidByPhone(String phone) {
        return employeeRepository.findByPhone(phone).isPresent();
    }

    public Optional<Employee> findById(Long memberId) {
        return employeeRepository.findById(memberId);
    }

    public List<Employee> findEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> findByName(String name) {
        return employeeRepository.findByName(name);
    }

    public Optional<Employee> findByPhone(String phone) {
        return employeeRepository.findByPhone(phone);
    }

    public Optional<Employee> findByAddress(String address) {
        return employeeRepository.findByAddress(address);
    }

    public List<Employee> search(String domain, String value) {
        if (domain.equals("name")) return employeeRepository.searchByName(value);
        if (domain.equals("phone")) return employeeRepository.searchByPhone(value);
        if (domain.equals("address")) return employeeRepository.searchByAddress(value);
        return null;
    }

    public boolean deleteVisitors() {
        return employeeRepository.deleteAll();
    }

    public boolean deleteOne(Long employeeId) {
        return employeeRepository.deleteById(employeeId);
    }

    public boolean update(Employee employee) {
        return employeeRepository.updateById(employee.getId(), employee.getName(), employee.getPhone(), employee.getAddress());
    }
}