package com.coffeegetaway.service.auth;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

public class Authorize {
    private String auth_url;

    public String getSessionId() {
        return SessionId;
    }

    public void setSessionId(String sessionId) {
        SessionId = sessionId;
    }

    private String SessionId = "";
    private String username = "";
    private String password = "";

    public Authorize(String url, String username, String password){
        this.auth_url=url;
        this.username = username;
        this.password = password;
        authorize();
    }

    public boolean isAuthorze(){
        return !SessionId.isEmpty();
    }

    public boolean authorize(){
        RestTemplate restTemplate = new RestTemplate();

        Map map = new HashMap();

        map.put("username", username);
        map.put("password", password);

        //add array
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(auth_url);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        HttpEntity<?> request = new HttpEntity<>(map, headers);


        ResponseEntity<String> responseEntity = restTemplate.exchange(
                builder.build().encode().toUri(),
                HttpMethod.POST,
                request,
                String.class);

        if (responseEntity.getStatusCode().value() == 200)
            SessionId = responseEntity.getHeaders().get("Authorization").get(0);

        if (SessionId.isEmpty())
            return false;
        return true;
    }
}
