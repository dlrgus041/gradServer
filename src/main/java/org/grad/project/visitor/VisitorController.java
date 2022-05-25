package org.grad.project.visitor;

import org.grad.project.model.Entry;
import org.grad.project.model.EntryForm;
import org.grad.project.system.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        List<Entry> visitors = visitorService.findVisitors();
        model.addAttribute("result", visitors);
        model.addAttribute("main", true);
        return "visitors/visitorList";
    }

    @PostMapping("/visitor/search")
    public String search(@RequestParam("domain") String domain, Model model, HttpServletRequest request) {

        String parameter = request.getParameter("value");
        List<Entry> list = visitorService.search(domain, parameter);
        model.addAttribute("result", list);
        model.addAttribute("main", false);

        return "visitors/visitorList";
    }

    @GetMapping("/visitor/update/false")
    public String createForm(Model model) {
        model.addAttribute("code", 0);
        model.addAttribute("modify", false);
        model.addAttribute("visitor", new Entry());
        return "visitors/updateVisitor";
    }

    @PostMapping("/visitor/update/{modify}")
    public String create(@PathVariable("modify") boolean modify, EntryForm form, Model model) {

        Entry visitor = new Entry();
        int mask = 1;

        if (form.getName().isEmpty()) mask |= (1 << 2);
        if (form.getPhone().isEmpty()) mask |= (1 << 3);
        if (form.getAddress1() * form.getAddress2() == 0) mask |= (1 << 4);
        if (!modify && visitorService.isValid(form.getPhone())) mask |= (1 << 6);

        visitor.setId(form.getId());
        visitor.setName(form.getName());
        visitor.setPhone(form.getPhone());
        visitor.setAddress(Table.codeToAddress(form.getAddress1(), form.getAddress2()));
        visitor.setCode(form.getAddress1() * 100 + form.getAddress2());

        model.addAttribute("visitor", visitor);
        model.addAttribute("modify", modify);

        for (int i = 1; i <= 6; i++) {
            if ((mask & (1 << i)) > 0) {
                model.addAttribute("code", i);
                return "visitors/updateVisitor";
            }
        }

        if (modify) visitorService.update(visitor);
        else visitorService.join(visitor);

        return "redirect:/visitor";
    }

//    @ResponseBody
//    @GetMapping("/visitor/send/{id}")
//    public String sendMessage(@PathVariable("id") int id) {
//
//        Optional<Entry> visitor = visitorService.findOne(id);
//        if (visitor.isEmpty()) return "error";
//
//        return "<script src=\"https://developers.kakao.com/sdk/js/kakao.js\"></script>\n" +
//               "<script type=\"text/javascript\">\n" +
//               "    if (confirm('해당 방문자에게 카카오톡 메시지를 전송하시겠습니까?')) {\n" +
//               "        Kakao.Auth.login({\n" +
//               "            scope: 'TALK_MESSAGE',\n" +
//               "            success: function (authObj) {\n" +
//               "                Kakao.API.request({\n" +
//               "                    url: '/v2/api/talk/memo/default/send',\n" +
//               "                    data: {\n" +
//               "                        template_object: {\n" +
//               "                            object_type: 'text',\n" +
//               "                            text:\n" +
//               "                                '아래의 정보를 확인하시고, 앱을 설치해서 QR코드를 발급받으세요.\\n'\n" +
//               "                                + 'ID :\\t' + id + '\\n'\n" +
//               "                                + '이름 :\\t' + name + '\\n'\n" +
//               "                                + '전화번호 :\\t' + phone + '\\n'\n" +
//               "                                + '주소 :\\t' + address,\n" +
//               "                            link: {\n" +
//               "                                mobile_web_url: 'https://developers.kakao.com',\n" +
//               "                                web_url: 'https://developers.kakao.com'\n" +
//               "                            },\n" +
//               "                            buttons: {\n" +
//               "                                title: 'App 다운로드하기',\n" +
//               "                                link: {\n" +
//               "                                    mobile_web_url: 'https://drive.google.com/file/d/1DW7Vuex0eMUI2gpEApFVQPl3YV0YQhJQ/view?usp=sharing',\n" +
//               "                                    web_url: 'https://drive.google.com/file/d/1DW7Vuex0eMUI2gpEApFVQPl3YV0YQhJQ/view?usp=sharing'\n" +
//               "                                }\n" +
//               "                            }\n" +
//               "                        },\n" +
//               "                    },\n" +
//               "                    success: function (res) {\n" +
//               "                        alert('메시지를 보냈습니다.\n방문자 목록 페이지로 돌아갑니다.')\n" +
//               "                    },\n" +
//               "                    fail: function (error) {\n" +
//               "                        alert('login success, but failed to request user information: ' + JSON.stringify(error))\n" +
//               "                    },\n" +
//               "                })\n" +
//               "            },\n" +
//               "            fail: function (err) {\n" +
//               "                alert('failed to login: ' + JSON.stringify(err))\n" +
//               "            },\n" +
//               "        })\n" +
//               "    }\n" +
//               "</script>";
//    }

    @GetMapping("/visitor/modify/{no}")
    public String modifyForm(@PathVariable("no") int no, Model model) {

        Optional<Entry> visitor = visitorService.findOne(no);
        if (visitor.isEmpty()) return "error";

        model.addAttribute("code", no);
        model.addAttribute("modify", true);
        model.addAttribute("visitor", visitor.get());
        return "visitors/updateVisitor";
    }

    @GetMapping("/visitor/delete/{no}")
    public String delete(@PathVariable("no") int no) {

        Optional<Entry> visitor = visitorService.findOne(no);
        if (visitor.isEmpty()) return "error";

        visitorService.deleteOne(no);
        return "redirect:/visitor";
    }
}
