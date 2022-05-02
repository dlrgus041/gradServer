package org.grad.project.employee;

import org.grad.project.model.Entry;
import org.grad.project.model.EntryForm;
import org.grad.project.system.Table;
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
        List<Entry> employees = employeeService.findEmployees();
        model.addAttribute("result", employees);
        model.addAttribute("main", true);
        return "employees/employeeList";
    }

    @PostMapping("/employee/search")
    public String search(@RequestParam("domain") String domain, Model model, HttpServletRequest request) {

        String parameter = request.getParameter("value");
        List<Entry> list = employeeService.search(domain, parameter);
        model.addAttribute("result", list);
        model.addAttribute("main", false);

        return "employees/employeeList";
    }

    @GetMapping("/employee/update")
    public String createForm(Model model) {
        model.addAttribute("code", 0);
        model.addAttribute("modify", false);
        model.addAttribute("employee", new Entry());
        return "employees/updateEmployee";
    }

    @PostMapping("/employee/update/{modify}/{code}")
    public String create(@PathVariable("modify") boolean modify, @PathVariable("code") int code,
                         EntryForm form, Model model) {

        Entry employee = new Entry();
        int mask = 1;

        if (modify) form.setId(code);

        if (form.getId() == 0) mask |= (1 << 1);
        if (form.getName().isEmpty()) mask |= (1 << 2);
        if (form.getPhone().isEmpty()) mask |= (1 << 3);
        if (form.getAddress1() * form.getAddress2() == 0) mask |= (1 << 4);
        if (!modify && employeeService.isValidById(form.getId())) mask |= (1 << 5);
        if (!modify && employeeService.isValidByPhone(form.getPhone())) mask |= (1 << 6);

        employee.setId(form.getId());
        employee.setName(form.getName());
        employee.setPhone(form.getPhone());
        employee.setAddress(Table.codeToAddress(form.getAddress1(), form.getAddress2()));
        employee.setCode(form.getAddress1() * 100 + form.getAddress2());

        model.addAttribute("employee", employee);
        model.addAttribute("modify", modify);

        for (int i = 1; i <= 6; i++) {
            if ((mask & (1 << i)) > 0) {
                model.addAttribute("code", i);
                return "employees/updateEmployee";
            }
        }

        if (modify) employeeService.update(employee);
        else employeeService.join(employee);

        return "redirect:/employee";
    }

    @GetMapping("/employee/modify/{no}")
    public String modifyGet(@PathVariable("no") int no, Model model) {

        Optional<Entry> employee = employeeService.findById(no);
        if (employee.isEmpty()) return "error";

        model.addAttribute("code", no);
        model.addAttribute("modify", true);
        model.addAttribute("employee", employee.get());
        return "employees/updateEmployee";
    }

    @GetMapping("/employee/delete/{no}")
    public String delete(@PathVariable("no") int no) {

        Optional<Entry> employee = employeeService.findById(no);
        if (employee.isEmpty()) return "error";

        employeeService.deleteOne(no);
        return "redirect:/employee";
    }
}
