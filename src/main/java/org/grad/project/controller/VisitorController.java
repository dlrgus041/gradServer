package org.grad.project.controller;

import org.grad.project.domain.Employee;
import org.grad.project.domain.Visitor;
import org.grad.project.form.VisitorForm;
import org.grad.project.service.VisitorService;
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
public class VisitorController {

    private final VisitorService visitorService;

    @Autowired
    public VisitorController(VisitorService visitorService) {
        this.visitorService = visitorService;
    }

    @GetMapping("/visitor")
    public String list(Model model) {
        List<Visitor> visitors = visitorService.findVisitors();
        model.addAttribute("result", visitors);
        model.addAttribute("main", true);
        return "visitors/visitorList";
    }

    @PostMapping("/visitor/search")
    public String search(@RequestParam("domain") String domain, Model model, HttpServletRequest request) {

        String parameter = request.getParameter("value");
        List<Visitor> list = visitorService.search(domain, parameter);
        model.addAttribute("result", list);
        model.addAttribute("main", false);

        return "visitors/visitorList";
    }

    @GetMapping("/visitor/update")
    public String createForm(Model model) {
        model.addAttribute("code", 0);
        model.addAttribute("result", new Visitor());
        return "visitors/updateVisitor";
    }

    @PostMapping("/visitor/update")
    public String create(VisitorForm form, Model model) {

        Visitor visitor = new Visitor();
        int mask = 1;

        if (form.getName().isEmpty()) mask |= (1 << 2);
        if (form.getPhone().isEmpty()) mask |= (1 << 3);
        if (form.getAddress().isEmpty()) mask |= (1 << 4);
        if (visitorService.isValid(form.getPhone())) mask |= (1 << 6);

        visitor.setName(form.getName());
        visitor.setPhone(form.getPhone());
        visitor.setAddress(form.getAddress());

        model.addAttribute("result", visitor);

        for (int i = 1; i <= 6; i++) {
            if ((mask & (1 << i)) > 0) {
                model.addAttribute("code", i);
                return "visitors/updateVisitor";
            }
        }

        visitorService.join(visitor);

        return "redirect:/visitor";
    }

    @GetMapping("/visitor/modify/{no}")
    public String modifyForm(@PathVariable("no") Long no, Model model) {

        Optional<Visitor> visitor = visitorService.findOne(no);
        if (visitor.isEmpty()) return "error";

        model.addAttribute("code", 9);
        model.addAttribute("result", visitor.get());
        return "visitors/updateVisitor";
    }

    @PostMapping("/visitor/modify/{no}")
    public String modify(@PathVariable("no") Long no, VisitorForm form) {

        Visitor visitor = new Visitor();
        visitorService.deleteOne(no);

        visitor.setName(form.getName());
        visitor.setPhone(form.getPhone());
        visitor.setAddress(form.getAddress());

        visitorService.join(visitor);

        return "redirect:/visitor";
    }

    @GetMapping("/visitor/delete/{no}")
    public String delete(@PathVariable("no") Long no) {

        Optional<Visitor> visitor = visitorService.findOne(no);
        if (visitor.isEmpty()) return "error";

        visitorService.deleteOne(no);
        return "redirect:/visitor";
    }
}
