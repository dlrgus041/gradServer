package org.grad.project.service;

import org.grad.project.domain.Employee;
import org.grad.project.domain.Visitor;
import org.grad.project.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Long join(Employee employee) {
        validateDuplicateEmployee(employee);
        employeeRepository.save(employee);
        return employee.getId();
    }

    private void validateDuplicateEmployee(Employee employee) {
        employeeRepository.findByName(employee.getName())
                .ifPresent(m -> {throw new IllegalStateException("이미 존재하는 회원입니다.");});
    }

    public List<Employee> findEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> findById(Long memberId) {
        return employeeRepository.findById(memberId);
    }

    public Optional<Employee> findByName(String name) {
        return employeeRepository.findByName(name);
    }

    public Optional<Employee> findByPhone(String phone) {
        return employeeRepository.findByPhone(phone);
    }

    public List<Employee> search(String domain, String value) {
        if (domain.equals("name")) return employeeRepository.searchByName(value);
        if (domain.equals("phone")) return employeeRepository.searchByPhone(value);
        if (domain.equals("address")) return employeeRepository.searchByAddress(value);
        return null;
    }

    public Optional<Employee> findByAddress(String address) {
        return employeeRepository.findByAddress(address);
    }

    public boolean deleteVisitors() {
        return employeeRepository.deleteAll();
    }

    public boolean deleteOne(Long visitorId) {
        return employeeRepository.deleteById(visitorId);
    }

    public boolean update(Visitor employee) {
        return employeeRepository.updateById(employee.getId(), employee.getName(), employee.getPhone(), employee.getAddress());
    }
}