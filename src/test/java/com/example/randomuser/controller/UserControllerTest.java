package com.example.randomuser.controller;
import com.example.randomuser.service.RandomUserService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RandomUserService randomUserService;

    @Test
    public void testGetRandomUser() throws Exception {
        JSONObject mockRandomUser = new JSONObject();
        mockRandomUser.put("name", "Ivan Petrov");
        mockRandomUser.put("email", "john.petrovich@example.com");

        when(randomUserService.getRandomUser()).thenReturn(mockRandomUser);

        mockMvc.perform(get("/random-user"))
                .andExpect(status().isOk())
                .andExpect(content().json(mockRandomUser.toString(2)));
    }
}
