package com.coffeegetaway.queue.consumer;

import com.coffeegetaway.queue.request.Request;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

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
            String msg = "Yeah... Success request send:" + request.getMethod().toString() + " " +
                    request.getUrl() + " at " + LocalDateTime.now().toString();
            System.out.println(msg);
        } catch (Exception e) {
            String msg = "Hmmm... Error sending request:" + request.getMethod().toString() + " " +
                    request.getUrl() + " at " + LocalDateTime.now().toString();
            System.out.println(msg);
            return false;
        }
        return result.getStatusCode().value() < 400;
    }
}
