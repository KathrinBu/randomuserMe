package com.example.randomuser.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;

@Service
public class RandomUserService {

    private final RestTemplate restTemplate;

    public RandomUserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public JSONObject getRandomUser() {
        String url = "https://randomuser.me/api/";
        String response = restTemplate.getForObject(url, String.class);
        return new JSONObject(response);
    }
}
