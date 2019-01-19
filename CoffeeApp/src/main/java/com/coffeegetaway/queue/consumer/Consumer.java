package com.coffeegetaway.queue.consumer;

import com.coffeegetaway.queue.request.Request;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class Consumer {
    public static boolean run(Request request) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<?> result;
        try {
            if (request.getClassName() != Object.class) {
                result = restTemplate.exchange(request.getUrl(),
                        request.getMethod(),
                        request.getRequest(),
                        request.getClassName());
            } else {
                result = restTemplate.exchange(request.getUrl(),
                        request.getMethod(),
                        request.getRequest(),
                        request.getParameterizedTypeReference());

            }
        } catch (Exception e) {
            return false;
        }
        return result.getStatusCode().value() < 400;
    }
}
