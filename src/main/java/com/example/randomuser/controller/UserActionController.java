package com.example.randomuser.controller;

import com.example.randomuser.service.UserActionRandomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-action")
public class UserActionController {
    private static final Logger logger = LoggerFactory.getLogger(UserActionController.class);
    @Autowired
    private UserActionRandomService userActionRandomService;

    @PostMapping
    public ResponseEntity<String> handleUserAction(@RequestParam("action") String action,
                                                   @RequestParam(value = "filePath", required = false) String filePath,
                                                   @RequestParam(value = "firstName", required = false) String firstName,
                                                   @RequestParam(value = "lastName", required = false) String lastName,
                                                   @RequestParam(value = "email", required = false) String email) {
        logger.info("Получен запрос с параметрами: action={}, filePath={}, firstName={}, lastName={}, email={}",
                action, filePath, firstName, lastName, email);
        if ("random".equals(action)) {
            userActionRandomService.loadRandomUsers(2);
        } else if ("load".equals(action)) {
            userActionRandomService.loadUsers(firstName, lastName, email);
        } else if ("export".equals(action)) {
            userActionRandomService.exportUsers(filePath);
        }
        return ResponseEntity.ok("Действие успешно выполнено.");
    }
}
