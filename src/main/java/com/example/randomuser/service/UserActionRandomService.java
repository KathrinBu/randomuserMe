package com.example.randomuser.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class UserActionRandomService {
    private final UserExportService userExportService;
    private final UserService userService;

    private final RandomUserService randomUserService;

    public UserActionRandomService(UserExportService userExportService, UserService userService,
                                   RandomUserService randomUserService) {
        this.userExportService = userExportService;
        this.userService = userService;
        this.randomUserService = randomUserService;
    }

    public void loadRandomUsers(int count) {
        for (int i = 0; i < count; i++) {
            JSONObject randomUser = randomUserService.getRandomUser();
            JSONArray results = randomUser.getJSONArray("results");
            if (results.length() > 0) {
                JSONObject userJson = results.getJSONObject(0);
                String firstName = userJson.getJSONObject("name").getString("first");
                String lastName = userJson.getJSONObject("name").getString("last");
                String email = userJson.getString("email");

                userService.saveUser(firstName, lastName, email);
            }
        }
    }

    public void loadUsers(String firstName, String lastName, String email) {
        userService.saveUser(firstName, lastName, email);
    }

    public void exportUsers(String filePath) {
        userExportService.exportToCSV(filePath);
    }
}
