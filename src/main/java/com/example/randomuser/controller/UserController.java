package com.example.randomuser.controller;
import com.example.randomuser.service.RandomUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.json.JSONObject;

@RestController
public class UserController {

    private final RandomUserService randomUserService;

    public UserController(RandomUserService randomUserService) {
        this.randomUserService = randomUserService;
    }

    @GetMapping("/random-user")
    public String getRandomUser() {
        JSONObject randomUser = randomUserService.getRandomUser();
        return randomUser.toString(2);
    }
}