package org.grad.project.log;

import org.grad.project.model.Log;
import org.grad.project.model.LogForm;
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

        logService.join(new Log(info, time, logService.checkID(toInt(info.get("ID"))), logService.checkTemp(toFloat(info.get("temp")))));

        Map<String, String> ret = new HashMap<>();
        ret.put("exist", toString(logService.checkID(toInt(info.get("ID")))));
        ret.put("within", toString(logService.checkTemp(toFloat(info.get("temp")))));

        return ret;
    }

    @GetMapping("/log")
    public String log(Model model) throws Exception {
        model.addAttribute("date", format(LocalDateTime.now(), "yyyy년 MM월 dd일"));
        model.addAttribute("result", logService.read());
        return "log/logList";
    }

// 삭제 예정 (테스트용) ------------------------------------------------------------------------------------------

    @GetMapping("/log/create")
    public String createForm(Model model) {
        model.addAttribute("code", 0);
        model.addAttribute("log", new LogForm());
        return "log/createLog";
    }

    @PostMapping("/log/create")
    public String create(LogForm form, Model model) {

        int mask = 1;

        if (form.getId() == 0) mask |= (1 << 1);
        if (form.getTemp() == 0) mask |= (1 << 2);
        if (form.getTime().isEmpty()) mask |= (1 << 3);

        model.addAttribute("log", form);

        for (int i = 1; i <= 3; i++) {
            if ((mask & (1 << i)) > 0) {
                model.addAttribute("code", i);
                return "log/createLog";
            }
        }

        logService.join(logService.makeLog(form));

        return "redirect:/log";
    }

// 삭제 예정 (테스트용) ----------------------------------------------------------------------------------------

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

    private String toString(boolean flag) {
        return String.valueOf(flag);
    }

    private int toInt(String str) {
        return Integer.parseInt(str);
    }

    private float toFloat(String str) {
        return Float.parseFloat(str);
    }
}
