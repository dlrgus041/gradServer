package org.grad.project.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LogController {

    private final String dateFormat = "yyyy년 MM월 dd일", timeFormat = "HH시 mm분 ss초";
    private final LogService logService;

    @Autowired
    public LogController(LogService logService) {
        this.logService = logService;
    }

    @ResponseBody
    @PostMapping(value = "/grad", produces = {"application/json"})
    public Map<String, Object> makeRepo(Map<String, Object> info) {

        System.out.println("ID: " + info.get("ID"));
        System.out.println("전화번호: " + info.get("phone"));
        System.out.println("주소: " + info.get("address"));
        System.out.println("체온: " + info.get("temp"));

        Map<String, Object> ret = new HashMap<>();

        ret.put("result", logService.check(info));

        return ret;
    }

//    @GetMapping("/log")
//    public String log(Model model) {
//
//        Log log = new Log();
//        log.setID();
//
//        LocalDateTime now = LocalDateTime.now();
//        model.addAttribute("date", print(now, dateFormat));
//        model.addAttribute("result", log);
//    }

    private String print(LocalDateTime now, String format) {
        return now.format(DateTimeFormatter.ofPattern(format));
    }
}
