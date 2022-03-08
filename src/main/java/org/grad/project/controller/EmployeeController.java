package org.grad.project.controller;

import org.grad.project.domain.Employee;
import org.grad.project.form.EmployeeForm;
import org.grad.project.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Controller
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employee")
    public String list(Model model) {
        List<Employee> employees = employeeService.findEmployees();
        model.addAttribute("result", employees);
        model.addAttribute("main", true);
        return "employees/employeeList";
    }

    @PostMapping("/employee/search")
    public String search(@RequestParam("domain") String domain, Model model, HttpServletRequest request) {

        String parameter = request.getParameter("value");
        List<Employee> list = employeeService.search(domain, parameter);
        model.addAttribute("result", list);
        model.addAttribute("main", false);

        return "employees/employeeList";
    }

    @GetMapping("/employee/update")
    public String createForm(Model model) {
        model.addAttribute("code", 0);
        model.addAttribute("result", new Employee());
        return "employees/updateEmployee";
    }

    @PostMapping("/employee/update")
    public String create(EmployeeForm form, Model model) {

        Employee employee = new Employee();
        int mask = 1;

        if (form.getId() == null) mask |= (1 << 1);
        if (form.getName().isEmpty()) mask |= (1 << 2);
        if (form.getPhone().isEmpty()) mask |= (1 << 3);
        if (form.getAddress().isEmpty()) mask |= (1 << 4);
        if (employeeService.isValidById(form.getId())) mask |= (1 << 5);
        if (employeeService.isValidByPhone(form.getPhone())) mask |= (1 << 6);

        employee.setId(form.getId());
        employee.setName(form.getName());
        employee.setPhone(form.getPhone());
        employee.setAddress(form.getAddress());
        employee.setVaccine(form.getVaccine());

        model.addAttribute("result", employee);

        for (int i = 1; i <= 6; i++) {
            if ((mask & (1 << i)) > 0) {
                model.addAttribute("code", i);
                return "employees/updateEmployee";
            }
        }

        employeeService.join(employee);

        return "redirect:/employee";
    }

    @GetMapping("/employee/modify/{no}")
    public String modifyGet(@PathVariable("no") Long no, Model model) {

        Optional<Employee> employee = employeeService.findById(no);
        if (employee.isEmpty()) return "error";

        model.addAttribute("code", 9);
        model.addAttribute("result", employee.get());
        return "employees/updateEmployee";
    }

    @PostMapping("/employee/modify/{no}")
    public String modifyPost(@PathVariable("no") Long no, EmployeeForm form) {

        Employee employee = new Employee();
        employeeService.deleteOne(no);

        employee.setId(no);
        employee.setName(form.getName());
        employee.setPhone(form.getPhone());
        employee.setAddress(form.getAddress());
        employee.setVaccine(form.getVaccine());

        employeeService.join(employee);

        return "redirect:/employee";
    }

    @GetMapping("/employee/delete/{no}")
    public String delete(@PathVariable("no") Long no) {

        Optional<Employee> employee = employeeService.findById(no);
        if (employee.isEmpty()) return "error";

        employeeService.deleteOne(no);
        return "redirect:/employee";
    }
}
