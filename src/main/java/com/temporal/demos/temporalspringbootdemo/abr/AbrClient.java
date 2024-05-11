package com.temporal.demos.temporalspringbootdemo.abr;

import com.temporal.demos.temporalspringbootdemo.RestTemplateWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

@Service
@RequiredArgsConstructor
@Slf4j
public class AbrClient {
    private final RestTemplateWrapper restTemplate;
    private final AbrClientConfiguration abrClientConfiguration;

    public String getAbr(String abrId) {
        var headers = createAuthHeaders(abrClientConfiguration.getUsername(), abrClientConfiguration.getPassword());
        var entity = new HttpEntity<>(headers);
        var url = abrClientConfiguration.getBaseUrl() + "/getAbrData/" + abrId;
        return restTemplate.get(url, entity, String.class);
    }

    HttpHeaders createAuthHeaders(String username, String password) {
        return new HttpHeaders() {{
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")), false);
            String authHeader = "Basic " + new String(encodedAuth);
            set("Authorization", authHeader);
        }};
    }
}
