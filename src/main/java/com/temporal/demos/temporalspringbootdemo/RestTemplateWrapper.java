package com.temporal.demos.temporalspringbootdemo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@RequiredArgsConstructor
@Slf4j
@Component
public class RestTemplateWrapper {
    final RestTemplate restTemplate = new RestTemplate();

    public <T> T get(String url, @Nullable HttpEntity<?> requestEntity, Class<T> responseType, Object... uriVariables) {
        return restTemplate.exchange(url, GET, requestEntity, responseType, uriVariables).getBody();
    }

    public <T> T post(String url, @Nullable HttpEntity<?> requestEntity, Class<T> responseType, Object... uriVariables) {
        return restTemplate.exchange(url, POST, requestEntity, responseType, uriVariables).getBody();
    }
}