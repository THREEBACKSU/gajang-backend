package cuk.api.Trinity;

import cuk.api.Trinity.Entity.TrinityUser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class TrinityRepository {
    private final RestTemplate restTemplate;
    private final static String BASE_PATH = "https://uportal.catholic.ac.kr";
    @Autowired
    public TrinityRepository(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public TrinityUser loginForm(TrinityUser trinityUser) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<?> httpEntity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(
                BASE_PATH + "/sso/jsp/sso/ip/login_form.jsp",
                HttpMethod.GET,
                httpEntity,
                String.class
        );

        Document document = Jsoup.parse(response.getBody());
        Element element = document.selectFirst("input[name=samlRequest]");

        String samlRequest = element.attr("value");
        trinityUser.setSamlRequest(samlRequest);

        HttpHeaders responseHeaders = response.getHeaders();
        List<String> cookies = responseHeaders.get(HttpHeaders.SET_COOKIE);

        if (cookies != null) {
            for (String cookie : cookies) {
                String[] nameValue = cookie.split("=", 2);
                if (nameValue[0].trim().equals("WMONID") || nameValue[0].trim().equals("SESSION_SSO")) {
                    String value = nameValue[1].split(";")[0].trim();
                    trinityUser.addCookie(nameValue[0].trim(), value);
                }
            }
        }

        return trinityUser;
    }

    public TrinityUser auth(TrinityUser trinityUser) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        httpHeaders.add("Cookie", trinityUser.getCookie());

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("userId", trinityUser.getTrinityId());
        params.add("password", trinityUser.getPassword());
        params.add("samlRequest", trinityUser.getSamlRequest());

        HttpEntity<?> httpEntity = new HttpEntity<>(params, httpHeaders);

        ResponseEntity<String> response = restTemplate.exchange(
                BASE_PATH + "/sso/processAuthnResponse.do",
                HttpMethod.POST,
                httpEntity,
                String.class
        );

        Document document = Jsoup.parse(response.getBody());
        Element element = document.selectFirst("input[name=SAMLResponse]");

        String SAMLResponse = element.attr("value");
        trinityUser.setSAMLResponse(SAMLResponse);

        HttpHeaders responseHeaders = response.getHeaders();
        List<String> cookies = responseHeaders.get(HttpHeaders.SET_COOKIE);

        if (cookies != null) {
            for (String cookie : cookies) {
                String[] nameValue = cookie.split("=", 2);
                String value = nameValue[1].split(";")[0].trim();
                trinityUser.addCookie(nameValue[0].trim(), value);
            }
        }

        return trinityUser;
    }

    public TrinityUser login(TrinityUser trinityUser) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        httpHeaders.add("Cookie", trinityUser.getCookie());

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("SAMLResponse", trinityUser.getSAMLResponse());

        HttpEntity<?> httpEntity = new HttpEntity<>(params, httpHeaders);

        ResponseEntity<String> response = restTemplate.exchange(
                BASE_PATH + "/portal/login/login.ajax",
                HttpMethod.POST,
                httpEntity,
                String.class
        );
        System.out.println("최초 요청: " + response.getBody());

//        Document document = Jsoup.parse(response.getBody());
//        Element element = document.selectFirst("meta[id=_csrf]");
//
//        String _csrf = element.attr("content");
//        trinityUser.set_csrf(_csrf);
        HttpHeaders responseHeaders = response.getHeaders();
        List<String> cookies = responseHeaders.get(HttpHeaders.SET_COOKIE);

        if (cookies != null) {
            for (String cookie : cookies) {
                String[] nameValue = cookie.split("=", 2);
                String value = nameValue[1].split(";")[0].trim();
                trinityUser.addCookie(nameValue[0].trim(), value);
            }
        }

        // 헤더 초기화 및 리다이렉션
        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        httpHeaders.add("Cookie", trinityUser.getCookie());

        httpEntity = new HttpEntity<>(params, httpHeaders);

        response = restTemplate.exchange(
                responseHeaders.getLocation().toString(),
                HttpMethod.GET,
                httpEntity,
                String.class
        );
        System.out.println("첫 번째 리다이렉션: " + response.getBody());

        responseHeaders = response.getHeaders();
        cookies = responseHeaders.get(HttpHeaders.SET_COOKIE);

        if (cookies != null) {
            for (String cookie : cookies) {
                String[] nameValue = cookie.split("=", 2);
                String value = nameValue[1].split(";")[0].trim();
                trinityUser.addCookie(nameValue[0].trim(), value);
            }
        }

        // 헤더 한번 더 초기화 및 리다이렉션
        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        httpHeaders.add("Cookie", trinityUser.getCookie());

        httpEntity = new HttpEntity<>(params, httpHeaders);

        response = restTemplate.exchange(
                responseHeaders.getLocation().toString(),
                HttpMethod.GET,
                httpEntity,
                String.class
        );
        System.out.println("두 번째 리다이렉션: " + response.getBody());

        System.out.println(response.getBody());

        return trinityUser;
    }
}
