//package org.grad.project.controller;
//
//import org.grad.project.domain.Employee;
//import org.grad.project.form.EmployeeForm;
//import org.grad.project.service.EmployeeService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.List;
//import java.util.Optional;
//
//@Controller
//public class EmployeeController2 {
//
//    private final EmployeeService employeeService;
//
//    @Autowired
//    public EmployeeController2(EmployeeService employeeService) {
//        this.employeeService = employeeService;
//    }
//
//    @GetMapping("/employee")
//    public String list(Model model) {
//        List<Employee> employees = employeeService.findEmployees();
//        model.addAttribute("employees", employees);
//        return "employees/employeeList";
//    }
//
//    @PostMapping("/employee/search")
//    public String search(@RequestParam("domain") String domain, Model model, HttpServletRequest request) {
//
//        String parameter = request.getParameter("value");
//        List<Employee> list = employeeService.search(domain, parameter);
//        model.addAttribute("search", list);
//
//        return "employees/searchEmployeeList";
//    }
//
//    @GetMapping("/employee/update/{code}")
//    public String createForm(@PathVariable("code") int code, Model model) {
//        model.addAttribute("code", code);
//        return "employees/updateEmployee";
//    }
//
//    @GetMapping("/employee/modify/{no}")
//    public String modifyForm(@PathVariable("no") Long no, Model model) {
//
//        Optional<Employee> employee = employeeService.findById(no);
//        if (employee.isEmpty()) return "error";
//
//        model.addAttribute("code", 9);
//        model.addAttribute("employee", employee.get());
//        return "employees/updateEmployee";
//    }
//
//    @PostMapping("/employee/update")
//    public String create(EmployeeForm form) {
//
//        if (form.getId() == null) return "redirect:/employee/update/1";
//        if (form.getName().isEmpty()) return "redirect:/employee/update/2";
//        if (form.getPhone().isEmpty()) return "redirect:/employee/update/3";
//        if (form.getAddress().isEmpty()) return "redirect:/employee/update/4";
//        if (employeeService.isValidById(form.getId())) return "redirect:/employee/update/5";
//        if (employeeService.isValidByPhone(form.getPhone())) return "redirect:/employee/update/6";
//
//        Employee employee = new Employee();
//
//        employee.setId(form.getId());
//        employee.setName(form.getName());
//        employee.setPhone(form.getPhone());
//        employee.setAddress(form.getAddress());
//        employee.setVaccine(form.getVaccine());
//
//        employeeService.join(employee);
//
//        return "redirect:/employee";
//    }
//
//    @PostMapping("/employee/modify/{no}")
//    public String modify(@PathVariable("no") Long no, EmployeeForm form) {
//
//        Employee employee = new Employee();
//        employeeService.deleteOne(no);
//
//        employee.setId(no);
//        employee.setName(form.getName());
//        employee.setPhone(form.getPhone());
//        employee.setAddress(form.getAddress());
//        employee.setVaccine(form.getVaccine());
//
//        employeeService.join(employee);
//
//        return "redirect:/employee";
//    }
//
//    @GetMapping("/employee/delete/{no}")
//    public String delete(@PathVariable("no") Long no) {
//
//        Optional<Employee> employee = employeeService.findById(no);
//        if (employee.isEmpty()) return "error";
//
//        employeeService.deleteOne(no);
//        return "redirect:/employee";
//    }
//}
