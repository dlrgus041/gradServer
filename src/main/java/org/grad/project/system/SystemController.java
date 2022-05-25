package org.grad.project.system;

import org.grad.project.log.Singleton;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Controller
public class SystemController {

    @GetMapping("/")
    public String home(Model model) {
        return "home";
    }

    @GetMapping("/login")
    public String login(@RequestParam("code") String code) {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", Singleton.getInstance().getKey());
        params.add("redirect_uri", "http://localhost:8080/login");
        params.add("code", code);

        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        header.add("Host", "kauth.kakao.com");

        String response = new RestTemplate()
                .exchange("https://kauth.kakao.com/oauth/token", HttpMethod.POST, new HttpEntity<>(params, header), String.class)
                .getBody();


        int startIndex = response.indexOf("\"access_token\":") + 16;
        int endIndex = response.indexOf(",", startIndex) - 1;

        Singleton.getInstance().setToken(response.substring(startIndex, endIndex));

        System.out.println(response);
        System.out.println(Singleton.getInstance().getToken());

        return "redirect:/";

    }

//        String requestUrl = "https://kauth.kakao.com/oauth/token";
//
//        HttpsURLConnection conn;
//        BufferedReader reader = null;
//        InputStreamReader isr = null;
//
//        try {
//            final URL url = new URL(requestUrl);
//            conn = (HttpsURLConnection) url.openConnection();
//            conn.setRequestMethod("POST");
//
//            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//            conn.setRequestProperty("charset", "utf-8");
//
//            final int responseCode = conn.getResponseCode();
//
//            System.out.println(String.format("\nSending '%s' request to URL : %s", httpMethod, requestUrl));
//            System.out.println("Response Code : " + responseCode);
//
//            if (responseCode == 200) {
//
//                isr = new InputStreamReader(conn.getInputStream());
//                reader = new BufferedReader(isr);
//
//                final StringBuilder buffer = new StringBuilder();
//                String line;
//
//                while ((line = reader.readLine()) != null) { buffer.append(line); }
//
//                return buffer.toString();
//
//            } else return "error";
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (reader != null) try { reader.close(); } catch (Exception ignore) { }
//            if (isr != null) try { isr.close(); } catch (Exception ignore) { }
//        }
//        return "error";
//    }
//
//    @PostMapping("/token")
//    public void setToken(@RequestBody Map<String, String> request) {
//        Singleton.getInstance().setToken(request.get("access_token"));
//    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }

}
