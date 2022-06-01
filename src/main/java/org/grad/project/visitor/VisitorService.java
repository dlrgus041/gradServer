package org.grad.project.visitor;

import org.grad.project.util.Util;
import org.grad.project.model.Entry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class VisitorService {

    private final VisitorRepository visitorRepository;

    @Autowired
    public VisitorService(VisitorRepository visitorRepository) {
        this.visitorRepository = visitorRepository;
    }

    public void join(Entry visitor) throws Exception {

        visitor.setId(visitorRepository.encrypt(2));

        Map<String, String> link = new HashMap<>();
        link.put("web_url", "https://drive.google.com/file/d/1HITifkeLT-IcwfiesKMwxx4nA6Ds0oVj/view?usp=sharing");
        link.put("mobile_web_url", "https://drive.google.com/file/d/1HITifkeLT-IcwfiesKMwxx4nA6Ds0oVj/view?usp=sharing");

        Map<String, Object> template_object = new HashMap<>();
        template_object.put("object_type", "text");
        template_object.put("text", makeText(visitor));
        template_object.put("link", link);
        template_object.put("button_title", "App 다운로드");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("template_object", Util.objectToString(template_object));
        params.add("receiver_uuids", Util.objectToString(new String[]{Util.getUuid()}));

        HttpHeaders header = new HttpHeaders();
        header.add("Host", "kapi.kakao.com");
        header.add("Authorization", "Bearer " + Util.getAccess().getToken());
        header.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        String response = Util.requestPost("https://kapi.kakao.com//v1/api/talk/friends/message/default/send", header, params);
        System.out.println(response);

        visitorRepository.save(visitor);
    }

    public boolean isValid(String phone) {
        return visitorRepository.findByPhone(phone).isPresent();
    }

    public List<Entry> findVisitors() {
        return visitorRepository.findAll();
    }

    public Optional<Entry> findOne(int memberId) {
        return visitorRepository.findById(memberId);
    }

    public List<Entry> search(String domain, String value) {
        if (domain.equals("name")) return visitorRepository.searchByName(value);
        if (domain.equals("phone")) return visitorRepository.searchByPhone(value);
        if (domain.equals("address")) return visitorRepository.searchByAddress(value);
        return null;
    }

    public boolean deleteOne(int visitorId) {
        return visitorRepository.deleteById(visitorId);
    }

    public boolean update(Entry visitor) {
        return visitorRepository.updateById(visitor.getId(), visitor.getName(), visitor.getPhone(), visitor.getAddress());
    }

    private String makeText(Entry visitor) {
        return "App을 다운로드한 뒤 아래 정보를 입력해서 QR코드를 발급받으세요.\n" +
                "ID :\t\t  " + visitor.getId() + "\n" +
                "이름 :\t\t" + visitor.getName() +"\n" +
                "전화번호 :\t" + visitor.getPhone() + "\n" +
                "주소 :\t\t" + visitor.getAddress();
    }
}
