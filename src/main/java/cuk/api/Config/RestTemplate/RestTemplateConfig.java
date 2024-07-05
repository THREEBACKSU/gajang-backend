package cuk.api.Config.RestTemplate;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Configuration
public class RestTemplateConfig {
    @Bean
    public RestTemplate restTemplate() {
        PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
        poolingHttpClientConnectionManager.setMaxTotal(50);
        poolingHttpClientConnectionManager.setDefaultMaxPerRoute(20);

        HttpClient httpClient = HttpClientBuilder.create()
                .setConnectionManager(poolingHttpClientConnectionManager)
//                .setRedirectStrategy(new LaxRedirectStrategy())
                .build();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        requestFactory.setConnectTimeout(5000);
        requestFactory.setReadTimeout(5000);

        ClientHttpRequestInterceptor interceptor = new ClientHttpRequestInterceptor() {
            @Override
            public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
                HttpHeaders httpHeaders = request.getHeaders();
                httpHeaders.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:72.0) Gecko/20100101 Firefox/72.0");
                httpHeaders.add("Accept", "*/*");
                httpHeaders.add("Accept-Encoding", "gzip, deflate, br");
                httpHeaders.add("Host", "uportal.catholic.ac.kr");
                return execution.execute(request, body);
            }
        };

        RestTemplate restTemplate = new RestTemplate(requestFactory);

        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
        if (interceptors == null) {
            restTemplate.setInterceptors(Collections.singletonList(interceptor));
        } else {
            interceptors.add(interceptor);
            restTemplate.setInterceptors(interceptors);
        }

        return restTemplate;
    }
}
