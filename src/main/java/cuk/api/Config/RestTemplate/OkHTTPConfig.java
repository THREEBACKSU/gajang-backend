package cuk.api.Config.RestTemplate;

import okhttp3.ConnectionPool;
import okhttp3.CookieJar;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.*;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.net.CookieManager;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
public class OkHTTPConfig {
    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
                .cookieJar(new JavaNetCookieJar(new CookieManager()))
                .followRedirects(true)
                .followSslRedirects(true)
                .build();
    }
}
