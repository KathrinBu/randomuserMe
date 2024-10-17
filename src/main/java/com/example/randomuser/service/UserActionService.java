package com.example.randomuser.service;

import com.example.randomuser.exception.UserActionException;
import com.example.randomuser.exception.UserExportException;
import com.example.randomuser.exception.UserServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserActionService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserExportService userExportService;

    public void processUserAction(String action, String filePath, String firstName, String lastName,
                                  String email) throws UserServiceException, UserExportException {
        switch (action) {
            case "load":
                handleUserSave(firstName, lastName, email);
                break;
            case "export":
                handleUserExport(filePath);
                break;
            default:
                throw new UserActionException("Недействительное действие: " + action);
        }
    }

    private void handleUserSave(String firstName, String lastName, String email) {
        if (firstName == null || lastName == null || email == null) {
            throw new UserActionException("Имя, фамилия и email должны быть указаны.");
        }
        userService.saveUser(firstName, lastName, email);
    }

    private void handleUserExport(String filePath) throws UserExportException {
        if (filePath == null || filePath.isEmpty()) {
            throw new UserActionException("Путь к файлу для экспорта не указан.");
        }
        userExportService.exportToCSV(filePath);
    }
}
