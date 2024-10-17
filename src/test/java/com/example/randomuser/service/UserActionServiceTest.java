package com.example.randomuser.service;
import org.junit.Test;

import com.example.randomuser.exception.UserActionException;
import com.example.randomuser.exception.UserServiceException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserActionServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private UserExportService userExportService;

    @InjectMocks
    private UserActionService userActionService;

    @Test
    public void testProcessUserActionSaveUserSuccess() throws Exception {
        String action = "load";
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";

        userActionService.processUserAction(action, null, firstName, lastName, email);

        verify(userService, times(1)).saveUser(firstName, lastName, email);
    }

    @Test
    public void testProcessUserActionExportSuccess() throws Exception {
        String action = "export";
        String filePath = "C:\\Users\\user\\Documents\\export.csv";

        userActionService.processUserAction(action, filePath, null, null, null);

        verify(userExportService, times(1)).exportToCSV(filePath);
    }

    @Test(expected = UserActionException.class)
    public void testHandleUserSaveMissingFieldsThrowsException() {
        String action = "load";
        String firstName = null;
        String lastName = "Doe";
        String email = "john.doe@example.com";

        userActionService.processUserAction(action, null, firstName, lastName, email);
    }

    @Test(expected = UserServiceException.class)
    public void testProcessUserActionSaveUserThrowsUserServiceException() throws Exception {
        String action = "load";
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";

        doThrow(new UserServiceException("Ошибка сохранения пользователя"))
                .when(userService).saveUser(firstName, lastName, email);
        userActionService.processUserAction(action, null, firstName, lastName, email);
    }
}
