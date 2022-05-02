package org.grad.project.log;

import org.grad.project.model.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public Map<String, String> makeRepo(@RequestBody Map<String, String> info) {

        LocalDateTime now = LocalDateTime.now();
        String time = format(now, "HH시 mm분 ss초");

        print(info, time);

        boolean result = logService.checkInfo(info);
        IO.getInstance().write(info, time, result);

        Map<String, String> ret = new HashMap<>();
        ret.put("result", String.valueOf(result));

        return ret;
    }

    @GetMapping("/log")
    public String log(Model model) throws Exception {
        model.addAttribute("date", format(LocalDateTime.now(), "yyyy년 MM월 dd일"));
        model.addAttribute("result", logService.read());
        return "log/logList";
    }

    @GetMapping("/log/create")
    public String createForm(Model model) {
        model.addAttribute("code", 0);
        model.addAttribute("modify", false);
        model.addAttribute("log", new Log());
        return "log/createLog";
    }

    @PostMapping("/log/create")
    public String create(Log form, Model model) {

        int mask = 1;

        if (form.getId().isEmpty()) mask |= (1 << 1);
        if (form.getTemp().isEmpty()) mask |= (1 << 2);
        if (form.getTime().isEmpty()) mask |= (1 << 3);
        if (!logService.checkID(Integer.parseInt(form.getId()))) mask |= (1 << 6);

        model.addAttribute("log", form);

        for (int i = 1; i <= 6; i++) {
            if ((mask & (1 << i)) > 0) {
                model.addAttribute("code", i);
                return "log/createLog";
            }
        }

        logService.join(form);

        return "redirect:/log";
    }

    private String format(LocalDateTime now, String format) {
        return now.format(DateTimeFormatter.ofPattern(format));
    }

    private void print(Map<String, String> info, String time) {
        System.out.println(time);
        System.out.println("ID: " + info.get("ID"));
        System.out.println("전화번호: " + info.get("phone"));
        System.out.println("주소: " + info.get("address"));
        System.out.println("체온: " + info.get("temp"));
        System.out.println("------------------------------");
    }
}
