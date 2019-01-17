package com.coffeegetaway.service.house;

import com.coffee.model.house.storage.StorageInfo;
import com.coffee.model.house.storage.StorageMiniInfo;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StorageServiceImpl implements StorageService {

    private String SessionId = "";

    private String default_urlTarget = "http://localhost:8080/storage/";

    @Override
    public StorageInfo findStorageById(Integer id) {
        RestTemplate restTemplate = new RestTemplate();
        String urlTarget = default_urlTarget + id.toString();
        StorageInfo result = null;
        try {
            result = restTemplate.getForObject(urlTarget, StorageInfo.class);

        } catch (Exception e) {
            if (authorize())
                return findStorageById(id);
        }
        return result;
    }

    @Override
    public List<StorageInfo> allStorage() {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", SessionId);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(default_urlTarget);
        HttpEntity<String> request = new HttpEntity<>("", headers);
        ResponseEntity<List<StorageInfo>> result = null;
        try {
            result = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET,
                    request,
                    new ParameterizedTypeReference<List<StorageInfo>>(){});
        } catch (Exception e) {
            if (authorize())
                return allStorage();
        }
        return result.getBody();
    }


    @Override
    public List<StorageInfo> allStorageForHouse(Integer houseId) {
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(default_urlTarget);
        builder.queryParam("house_id", houseId);

        ResponseEntity<List<StorageInfo>> result = null;
        try {
            result = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<StorageInfo>>(){});
        } catch (Exception e) {
            if (authorize())
                return allStorageForHouse(houseId);
        }
        return result.getBody();
    }

    @Override
    public void deleteStorage(Integer id) {
        String urlTarget = default_urlTarget + "{id}";
        Map<String, String> params = new HashMap<>();
        params.put("id", id.toString());

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete (urlTarget,  params );
    }

    @Override
    public ResponseEntity<StorageInfo> updateStorage(StorageMiniInfo storageInfo, Integer id) {
        String urlTarget = default_urlTarget + id.toString();
        HttpEntity<StorageMiniInfo> request = new HttpEntity<>(storageInfo);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<StorageInfo> result = restTemplate.exchange(urlTarget, HttpMethod.PUT, request, StorageInfo.class);
        if (result == null) {
            authorize();
            return updateStorage(storageInfo, id);
        }
        return result;
    }

    @Override
    public ResponseEntity<StorageInfo> createStorage(StorageMiniInfo storageInfo) {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<StorageMiniInfo> request = new HttpEntity<>(storageInfo);
        StorageInfo result = restTemplate.postForObject(default_urlTarget, request, StorageInfo.class);
        if (result == null) {
            authorize();
            return createStorage(storageInfo);
        }
        return new ResponseEntity<StorageInfo>(result, HttpStatus.CREATED);
    }


    private boolean authorize(){
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();

        Map req_payload = new HashMap();

        req_payload.put("username", "getaway");
        req_payload.put("password", "getaway-house");

        //add array
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/auth");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        HttpEntity<?> request = new HttpEntity<>(req_payload, headers);


        ResponseEntity<String> responseEntity = restTemplate.exchange(
                builder.build().encode().toUri(),
                HttpMethod.POST,
                request,
                String.class);

        if (responseEntity.getStatusCode().value() != 200)
            return false;
        SessionId = responseEntity.getHeaders().get("Authorization").get(0).toString();
        return true;
    }
}
