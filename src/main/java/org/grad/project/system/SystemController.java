package org.grad.project.system;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.grad.project.util.Util;

@Controller
public class SystemController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("login", Util.isInit());
        model.addAttribute("status", Util.getAccess().isValid());
        model.addAttribute("refresh", false);
        return "home";
    }

    @GetMapping("/login")
    public ModelAndView login(@RequestParam("code") String code) throws Exception {

        Util.login(code);

        ModelAndView mav = new ModelAndView();
        mav.setViewName("home");
        mav.addObject("login", Util.isInit());
        mav.addObject("status", Util.getAccess().isValid());
        mav.addObject("refresh", false);

        return mav;
    }

    @GetMapping("/refresh")
    public ModelAndView refresh() throws Exception {

        Util.refresh();

        ModelAndView mav = new ModelAndView();
        mav.setViewName("home");
        mav.addObject("login", Util.isInit());
        mav.addObject("status", Util.getAccess().isValid());
        mav.addObject("refresh", true);

        return mav;
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }

}
