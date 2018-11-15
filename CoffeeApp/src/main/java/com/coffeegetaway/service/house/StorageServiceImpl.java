package com.coffeegetaway.service.house;

import com.coffee.model.house.storage.StorageInfo;
import com.coffee.model.house.storage.StorageMiniInfo;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class StorageServiceImpl implements StorageService {

    private String default_urlTarget = "http://localhost:8080/storage/";

    @Override
    public StorageInfo findStorageById(Integer id) {
        RestTemplate restTemplate = new RestTemplate();
        String urlTarget = default_urlTarget + id.toString();
        StorageInfo result = restTemplate.getForObject(urlTarget, StorageInfo.class);
        return result;
    }

    @Override
    public List<StorageInfo> allStorage(Optional<Integer> houseId) {
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(default_urlTarget);
        if (houseId.isPresent()) {
            builder.queryParam("house_id", houseId);
        }

        ResponseEntity<List<StorageInfo>> result = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<StorageInfo>>(){});
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
            return result;
        }

    @Override
    public ResponseEntity<StorageInfo> createStorage(StorageMiniInfo storageInfo) {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<StorageMiniInfo> request = new HttpEntity<>(storageInfo);
        StorageInfo result = restTemplate.postForObject(default_urlTarget, request, StorageInfo.class);
        if (result == null)
            return new ResponseEntity<>((StorageInfo) null, HttpStatus.NOT_ACCEPTABLE);
        return new ResponseEntity<StorageInfo>(result, HttpStatus.CREATED);
    }
}
