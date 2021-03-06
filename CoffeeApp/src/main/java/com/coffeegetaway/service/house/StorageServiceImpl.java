package com.coffeegetaway.service.house;

import com.coffee.model.house.storage.StorageInfo;
import com.coffee.model.house.storage.StorageMiniInfo;
import com.coffeegetaway.ErrorModel;
import com.coffeegetaway.service.auth.Authorize;
import com.google.gson.Gson;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StorageServiceImpl implements StorageService {

    private String default_urlTarget = "http://localhost:8080/storage/";

    private Authorize auth = new Authorize("http://localhost:8080/auth", "getaway", "getaway-house");

    @Override
    public StorageInfo findStorageById(Integer id) {
        if (!auth.isAuthorze())
            auth.authorize();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", auth.getSessionId());
        HttpEntity entity = new HttpEntity(headers);

        String urlTarget = default_urlTarget + id.toString();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(urlTarget);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<StorageInfo> result = null;
        try {
            result = restTemplate.exchange(
                    builder.build().encode().toUri(), HttpMethod.GET, entity, StorageInfo.class);
        }catch (HttpClientErrorException ex) {
            Gson gs = new Gson();
            ErrorModel rr = gs.fromJson(ex.getResponseBodyAsString(), ErrorModel.class);
            throw new ResponseStatusException(ex.getStatusCode(), rr.getMessage(), ex.getCause());
        } catch (ResourceAccessException ex) {
            throw new ResponseStatusException(HttpStatus.FAILED_DEPENDENCY, "Full information temporarily unavailable", ex);
        }
        return result.getBody();
    }

    @Override
    public List<StorageInfo> allStorage() {

        if (!auth.isAuthorze())
            auth.authorize();
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", auth.getSessionId());

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(default_urlTarget);
        HttpEntity request = new HttpEntity(headers);
        ResponseEntity<List<StorageInfo>> result;
        try {
            result = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET,
                    request,
                    new ParameterizedTypeReference<List<StorageInfo>>() {
                    });
        } catch (ResourceAccessException ex) {
            throw new ResponseStatusException(HttpStatus.FAILED_DEPENDENCY, "Full information temporarily unavailable", ex);
        }
        return result.getBody();
    }


    @Override
    public List<StorageInfo> allStorageForHouse(Integer houseId) {
        if (!auth.isAuthorze())
            auth.authorize();

        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(default_urlTarget);
        builder.queryParam("house_id", houseId);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", auth.getSessionId());
        HttpEntity request = new HttpEntity(headers);
        ResponseEntity<List<StorageInfo>> result;
        try {
            result = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET,
                    request,
                    new ParameterizedTypeReference<List<StorageInfo>>() {
                    });
        } catch (HttpClientErrorException ex) {
            Gson gs = new Gson();
            ErrorModel rr = gs.fromJson(ex.getResponseBodyAsString(), ErrorModel.class);
            throw new ResponseStatusException(ex.getStatusCode(), rr.getMessage(), ex.getCause());
        } catch (ResourceAccessException ex) {
            throw new ResponseStatusException(HttpStatus.FAILED_DEPENDENCY, "Full information temporarily unavailable", ex);
        }
        return result.getBody();
    }

    @Override
    public void deleteStorage(Integer id) {
        if (!auth.isAuthorze())
            auth.authorize();

        String urlTarget = default_urlTarget + id.toString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", auth.getSessionId());
        HttpEntity request = new HttpEntity( headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(urlTarget);

        RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.DELETE,
                    request,
                    String.class);
        } catch (HttpClientErrorException ex) {
            Gson gs = new Gson();
            ErrorModel rr = gs.fromJson(ex.getResponseBodyAsString(), ErrorModel.class);
            throw new ResponseStatusException(ex.getStatusCode(), rr.getMessage(), ex.getCause());
        } catch (ResourceAccessException ex) {
            throw new ResponseStatusException(HttpStatus.FAILED_DEPENDENCY, "Full information temporarily unavailable", ex);
        }
    }

    @Override
    public ResponseEntity<StorageInfo> updateStorage(StorageMiniInfo storageInfo, Integer id) {
        if (!auth.isAuthorze())
            auth.authorize();

        String urlTarget = default_urlTarget + id.toString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", auth.getSessionId());
        HttpEntity<StorageMiniInfo> request = new HttpEntity<>(storageInfo, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<StorageInfo> result;
        try {
            result = restTemplate.exchange(urlTarget, HttpMethod.PUT, request, StorageInfo.class);
        } catch (HttpClientErrorException ex) {
            Gson gs = new Gson();
            ErrorModel rr = gs.fromJson(ex.getResponseBodyAsString(), ErrorModel.class);
            throw new ResponseStatusException(ex.getStatusCode(), rr.getMessage(), ex.getCause());
        } catch (ResourceAccessException ex) {
            throw new ResponseStatusException(HttpStatus.FAILED_DEPENDENCY, "Full information temporarily unavailable", ex);
        }
        return result;
    }

    @Override
    public ResponseEntity<StorageInfo> createStorage(StorageMiniInfo storageInfo) {
        if (!auth.isAuthorze())
            auth.authorize();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", auth.getSessionId());
        HttpEntity<StorageMiniInfo> request = new HttpEntity<>(storageInfo, headers);

        RestTemplate restTemplate = new RestTemplate();
        StorageInfo result;
        try {
            result = restTemplate.postForObject(default_urlTarget, request, StorageInfo.class);
        } catch (HttpClientErrorException ex) {
            Gson gs = new Gson();
            ErrorModel rr = gs.fromJson(ex.getResponseBodyAsString(), ErrorModel.class);
            throw new ResponseStatusException(ex.getStatusCode(), rr.getMessage(), ex.getCause());
        } catch (ResourceAccessException ex) {
            throw new ResponseStatusException(HttpStatus.FAILED_DEPENDENCY, "Full information temporarily unavailable", ex);
        }
        return new ResponseEntity<StorageInfo>(result, HttpStatus.CREATED);
    }

}
