package org.grad.project;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class Util {

    static private String token = null;
    static private final String key = "51e791da45820b34f137b8d9c87e733e";
    static private final RestTemplate rt = new RestTemplate();
    static private final ObjectMapper mapper = new ObjectMapper();

    static public String getKey() {
        return key;
    }

    static public String getToken() {
        return token;
    }

    static public void setToken(String response, String key) {
        int startIndex = response.indexOf(key) + key.length() + 3;
        int endIndex = response.indexOf(",", startIndex) - 1;
        token = response.substring(startIndex, endIndex);
    }

    static public boolean isTokenValid() {
        return token != null;
    }

    static public String request(String url, HttpMethod method, HttpHeaders header, MultiValueMap<String, String> params) {
        return rt.exchange(url, method, new HttpEntity<>(params, header), String.class).getBody();
    }

    static public String objectToString(Object object) throws Exception {
        return mapper.writeValueAsString(object);
    }
}