package org.grad.project.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SystemController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }
}
