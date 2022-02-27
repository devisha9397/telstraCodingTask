package com.telstra.codechallenge.oldestuser;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    @Value("${oldUser.base.url}")
    private String UserBaseUrl;

    private RestTemplate restTemplate;

    private ObjectMapper objectMapper;

    public UserService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * Returns an array of github users with zero followers.
     *
     * @return - a User array
     */
    public UsersData getUsersWithZeroFollowers(int limit) throws Exception {
        validateLimit(limit);
        UsersData responseData = new UsersData();
        responseData.setRequestedCount(limit);
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        int page = getPageByLimit(limit);
        int per_page = 100;
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        String urlTemplate = UriComponentsBuilder.fromHttpUrl(UserBaseUrl)
                .queryParam("q", "{q}")
                .queryParam("sort", "{sort}")
                .queryParam("order", "{order}")
                .queryParam("per_page", "{per_page}")
                .queryParam("page", "{page}")
                .encode()
                .toUriString();

        for (int i = 1; i <= page; i++) {
            per_page = i==page && (limit % 100) != 0 ? limit % 100 : 100;
            Map<String, Object> params = new HashMap<>();
            params.put("q", "followers:0");
            params.put("sort", "joined");
            params.put("order", "asc");
            params.put("per_page", per_page);
            params.put("page", i);
            HttpEntity<String> response = restTemplate.exchange(
                    urlTemplate,
                    HttpMethod.GET,
                    entity,
                    String.class,
                    params
            );
            UsersData data = objectMapper.readValue(response.getBody(), UsersData.class);
            responseData.addIteams(data.getItems());
        }
        responseData.setReceivedCount(responseData.getItems().size());
        return responseData;
    }

    private void validateLimit(int limit) {
        if(limit <= 0){
            throw new CustomException("Limit should be greater than 0", HttpStatus.CONFLICT,
                    HttpStatus.CONFLICT.value());
        }
    }

    private int getPageByLimit(int limit) {
        int page = 1;
        if ((limit / 100) > 0) {
            page = limit / 100;
            if ((limit % 100) != 0) {
                page++;
            }
        }
        return page;
    }
}
