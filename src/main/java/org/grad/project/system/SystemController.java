package org.grad.project.system;

import org.grad.project.Util;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SystemController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("login", false);
        model.addAttribute("status", Util.isTokenValid());
        return "home";
    }

    @GetMapping("/login")
    public ModelAndView login(@RequestParam("code") String code) {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", Util.getKey());
        params.add("redirect_uri", "http://localhost:8080/login");
        params.add("code", code);

        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        header.add("Host", "kauth.kakao.com");

        String response = Util.request("https://kauth.kakao.com/oauth/token", HttpMethod.POST, header, params);

        Util.setToken(response, "access_token");
        System.out.println("token: " + Util.getToken());

        ModelAndView mav = new ModelAndView();
        mav.setViewName("home");
        mav.addObject("login", true);
        mav.addObject("status", Util.isTokenValid());

        return mav;
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }

}
