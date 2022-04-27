package org.grad.project.log;

import org.grad.project.system.IO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LogController {

    private final LogService logService;

    @Autowired
    public LogController(LogService logService) {
        this.logService = logService;
    }

    @ResponseBody
    @PostMapping(value = "/grad", produces = {"application/json"})
    public Map<String, Object> makeRepo(Map<String, Object> info) {

        LocalDateTime now = LocalDateTime.now();
        String time = format(now, "HH시 mm분 ss초");

        System.out.println(time);
        System.out.println("ID: " + info.get("ID"));
        System.out.println("전화번호: " + info.get("phone"));
        System.out.println("주소: " + info.get("address"));
        System.out.println("체온: " + info.get("temp"));
        System.out.println("------------------------------");

        Map<String, Object> ret = new HashMap<>();

        boolean result = logService.check(info);
        if (result) IO.getInstance().write(info, time);

        ret.put("result", result);

        return ret;
    }

    @GetMapping("/log")
    public String log(Model model) throws Exception {
        model.addAttribute("date", format(LocalDateTime.now(), "yyyy년 MM월 dd일"));
        model.addAttribute("result", logService.read());
        return "log";
    }

    private String format(LocalDateTime now, String format) {
        return now.format(DateTimeFormatter.ofPattern(format));
    }
}
