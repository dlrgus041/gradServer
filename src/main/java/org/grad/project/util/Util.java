package org.grad.project.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.grad.project.dto.FriendResponse;
import org.grad.project.dto.TokenResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class Util {

    static private final Token access = new Token();
    static private final Token refresh = new Token();

    static private final String key = "55be62f097c7e69a2ff9b58382f72259";
    static private String uuid = null;

    static private final RestTemplate rt = new RestTemplate();
    static private final ObjectMapper mapper = new ObjectMapper();

    static public Token getAccess() {
        return access;
    }

    static public Token getRefresh() {
        return refresh;
    }

    static public String getKey() {
        return key;
    }

    static public String getUuid() {
        return uuid;
    }

    static public boolean isInit() {
        return access.isValid() && uuid != null;
    }

    static public void login(String code) throws Exception {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", Util.getKey());
        params.add("redirect_uri", "http://uouitcparkjh1998.ddns.net:8080/login");
        params.add("code", code);

        HttpHeaders header1 = new HttpHeaders();
        header1.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        header1.add("Host", "kauth.kakao.com");

        System.out.println("------------------------------");

        TokenResponse tr = stringToObject(requestPost("https://kauth.kakao.com/oauth/token", header1, params), TokenResponse.class);

        access.setToken(tr.access_token);
        access.setExpiresIn(tr.expires_in);
        access.setTimeStamp(System.currentTimeMillis());

        refresh.setToken(tr.refresh_token);
        refresh.setExpiresIn(tr.refresh_token_expires_in);
        refresh.setTimeStamp(System.currentTimeMillis());

        System.out.println("token: " + access.getToken());
        System.out.println("expires_in: " + access.getExpiresIn() + " sec");

        HttpHeaders header2 = new HttpHeaders();
        header2.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        header2.add("Host", "kapi.kakao.com");
        header2.add("Authorization", "Bearer " + access.getToken());

        uuid = stringToObject(requestGet("https://kapi.kakao.com/v1/api/talk/friends", header2), FriendResponse.class).elements.get(1).uuid;
        System.out.println("uuid: " + uuid);

        System.out.println("------------------------------");
    }

    static public void refresh() throws Exception {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "refresh_token");
        params.add("client_id", key);
        params.add("refresh_token", refresh.getToken());

        HttpHeaders header = new HttpHeaders();
        header.add("Host", "kauth.kakao.com");
        header.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        System.out.println("------------------------------");
        System.out.println("refresh");

        TokenResponse tr = stringToObject(requestPost("https://kauth.kakao.com/oauth/token", header, params), TokenResponse.class);

        access.setToken(tr.access_token);
        access.setExpiresIn(tr.expires_in);
        access.setTimeStamp(System.currentTimeMillis());

        if (!refresh.isValid()) {
            refresh.setToken(tr.refresh_token);
            refresh.setExpiresIn(tr.refresh_token_expires_in);
            refresh.setTimeStamp(System.currentTimeMillis());
        }

        System.out.println("token: " + access.getToken());
        System.out.println("expires_in: " + access.getExpiresIn() + " sec");

        System.out.println("------------------------------");
    }

    static public String requestPost(String url, HttpHeaders header, MultiValueMap<String, String> params) {
        return rt.exchange(url, HttpMethod.POST, new HttpEntity<>(params, header), String.class).getBody();
    }
    static public String requestGet(String url, HttpHeaders header) {
        return rt.exchange(url, HttpMethod.GET, new HttpEntity<>(header), String.class).getBody();
    }

    static public String objectToString(Object object) throws Exception {
        return mapper.writeValueAsString(object);
    }

    static public <T> T stringToObject(String body, Class<T> object) throws Exception {
        return mapper.readValue(body, object);
    }
}