package cuk.api.Trinity;

import cuk.api.Trinity.Entity.TrinityUser;
import okhttp3.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class TrinityRepository {
    private final OkHttpClient client;
    private final static String BASE_PATH = "https://uportal.catholic.ac.kr";
    @Autowired
    public TrinityRepository(OkHttpClient okHttpClient) {
        this.client = okHttpClient;
    }

    public TrinityUser loginForm(TrinityUser trinityUser) throws Exception {
        Request request = new Request.Builder()
                .url(BASE_PATH + "/sso/jsp/sso/ip/login_form.jsp")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:72.0) Gecko/20100101 Firefox/72.0")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                Document document = Jsoup.parse(response.body().string());
                Elements body = document.getElementsByTag("input");
                for (Element element : body) {
                    if ("samlRequest".equals(element.attr("name"))) {
                        System.out.println(element.attr("value"));
                        trinityUser.setSamlRequest(element.attr("value"));
                        break;
                    }
                }
            } else {
                System.out.println("Request failed: " + response.message());  // 실패 메시지 출력
            }
        }

        return trinityUser;
    }

    public TrinityUser auth(TrinityUser trinityUser) throws Exception {
        RequestBody formBody = new FormBody.Builder()
                .add("userId", trinityUser.getTrinityId())
                .add("password", trinityUser.getPassword())
                .add("samlRequest", trinityUser.getSamlRequest())
                .build();

        Request request = new Request.Builder()
                .url(BASE_PATH + "/sso/processAuthnResponse.do")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:72.0) Gecko/20100101 Firefox/72.0")
                .post(formBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                Document document = Jsoup.parse(response.body().string());
                Elements body = document.getElementsByTag("input");
                for (Element element : body) {
                    if ("SAMLResponse".equals(element.attr("name"))) {
                        System.out.println(element.attr("value"));
                        trinityUser.setSAMLResponse(element.attr("value"));
                        break;
                    }
                }
            } else {
                System.out.println("Request failed: " + response.message());  // 실패 메시지 출력
            }
        }

        return trinityUser;
    }

    public TrinityUser login(TrinityUser trinityUser) throws Exception {
        RequestBody formBody = new FormBody.Builder()
                .add("SAMLResponse", trinityUser.getSAMLResponse())
                .build();

        Request request = new Request.Builder()
                .url(BASE_PATH + "/portal/login/login.ajax")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:72.0) Gecko/20100101 Firefox/72.0")
                .post(formBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                Document document = Jsoup.parse(response.body().string());
                Elements body = document.getElementsByTag("meta");
                for (Element element : body) {
                    if ("_csrf".equals(element.attr("id"))) {
                        System.out.println(element.attr("value"));
                        trinityUser.set_csrf(element.attr("value"));
                        break;
                    }
                }
            } else {
                System.out.println("Request failed: " + response.message());  // 실패 메시지 출력
            }
        }
        return trinityUser;
    }
}
