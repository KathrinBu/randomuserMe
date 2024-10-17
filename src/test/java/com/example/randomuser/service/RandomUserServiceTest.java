package com.example.randomuser.service;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class RandomUserServiceTest {
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private RandomUserService randomUserService;

    @Test
    public void testGetRandomUser() throws JSONException {
        String mockApiResponse = "{ \"results\": [{ \"name\": { \"first\": \"Ivan\", \"last\": \"Petrov\" }, " +
                "\"email\": \"john.petrovich@example.com\" }] }";

        when(restTemplate.getForObject(anyString(), any(Class.class))).thenReturn(mockApiResponse);

        JSONObject result = randomUserService.getRandomUser();
        JSONArray resultsArray = result.getJSONArray("results");
        JSONObject userObject = resultsArray.getJSONObject(0);
        JSONObject nameObject = userObject.getJSONObject("name");

        String firstName = nameObject.getString("first");
        String lastName = nameObject.getString("last");
        String email = userObject.getString("email");

        assertEquals("Ivan", firstName);
        assertEquals("Petrov", lastName);
        assertEquals("john.petrovich@example.com", email);
    }
}