package org.grad.project.entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class EntryController {

    private final EntryService entryService;

    @Autowired
    public EntryController(EntryService entryService) {
        this.entryService = entryService;
    }

    @GetMapping("/entry")
    public String entry(Model model) {
        model.addAttribute("result", entryService.findEmployees());
        model.addAttribute("main", true);
        return "entryList";
    }

    @PostMapping("/entry/search")
    public String search(@RequestParam("domain") String domain, Model model, HttpServletRequest request) {

        String parameter = request.getParameter("value");
        List<Entry> list = entryService.search(domain, parameter);
        model.addAttribute("result", list);
        model.addAttribute("main", false);

        return "entryList";
    }
}
