package com.example.randomuser.service;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class UserActionRandomServiceTest {

    @Mock
    private UserExportService userExportService;

    @Mock
    private UserService userService;

    @Mock
    private RandomUserService randomUserService;

    @InjectMocks
    private UserActionRandomService userActionRandomService;

    @Test
    public void testLoadRandomUsersSuccess() throws JSONException {
        String mockApiResponse = "{ \"results\": [{ \"name\": { \"first\": \"Ivan\", \"last\": \"Petrov\" }, " +
                "\"email\": \"john.petrovich@example.com\" }] }";
        JSONObject mockResponseJson = new JSONObject(mockApiResponse);

        when(randomUserService.getRandomUser()).thenReturn(mockResponseJson);
        userActionRandomService.loadRandomUsers(1);
        verify(userService, times(1)).saveUser("Ivan", "Petrov",
                "john.petrovich@example.com");
    }

    @Test
    public void testLoadUsersSuccess() {
        String firstName = "Ivan";
        String lastName = "Petrov";
        String email = "john.petrovich@example.com";

        userActionRandomService.loadUsers(firstName, lastName, email);
        verify(userService, times(1)).saveUser(firstName, lastName, email);
    }

    @Test
    public void testExportUsersSuccess() {
        String filePath = "C:\\Users\\user\\Documents\\export.csv";
        userActionRandomService.exportUsers(filePath);
        verify(userExportService, times(1)).exportToCSV(filePath);
    }
}
