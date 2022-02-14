package org.grad.project.controller;

import org.grad.project.domain.Visitor;
import org.grad.project.form.VisitorForm;
import org.grad.project.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
        model.addAttribute("visitors", visitors);
        return "visitors/visitorList";
    }

    @GetMapping("/visitor/create")
    public String createForm() {
        return "visitors/createVisitorForm";
    }

    @PostMapping("/visitor/create")
    public String create(VisitorForm form) {

        Visitor visitor = new Visitor();
        visitor.setName(form.getName());
        visitor.setPhone(form.getPhone());
        visitor.setAddress(form.getAddress());

        visitorService.join(visitor);

        return "redirect:/visitor";
    }

    @GetMapping("/visitor/modify/{no}")
    public String modifyForm(@PathVariable("no") Long no, Model model) {

        Optional<Visitor> visitor = visitorService.findOne(no);
        if (visitor.isEmpty()) return "error";

        model.addAttribute("visitor", visitor.get());
        return "visitors/modifyVisitorForm";
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
