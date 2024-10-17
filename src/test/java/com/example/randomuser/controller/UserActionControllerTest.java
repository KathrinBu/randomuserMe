package com.example.randomuser.controller;

import com.example.randomuser.exception.UserActionException;
import com.example.randomuser.service.UserActionRandomService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserActionController.class)
public class UserActionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserActionRandomService userActionRandomService;


    @Test
    public void testHandleUserActionLoadRandomSuccess() throws Exception {
        String action = "random";

        doNothing().when(userActionRandomService).loadRandomUsers(2);

        mockMvc.perform(post("/user-action")
                        .param("action", action))
                .andExpect(status().isOk())
                .andExpect(content().string("Действие успешно выполнено."));

        verify(userActionRandomService, times(1)).loadRandomUsers(2);
    }

    @Test
    public void testHandleUserActionLoadUserSuccess() throws Exception {
        String action = "load";
        String firstName = "Ivan";
        String lastName = "Petrov";
        String email = "john.petrovich@example.com";

        doNothing().when(userActionRandomService).loadUsers(firstName, lastName, email);

        mockMvc.perform(post("/user-action")
                        .param("action", action)
                        .param("firstName", firstName)
                        .param("lastName", lastName)
                        .param("email", email))
                .andExpect(status().isOk())
                .andExpect(content().string("Действие успешно выполнено."));

        verify(userActionRandomService, times(1))
                .loadUsers(firstName, lastName, email);
    }

    @Test
    public void testHandleUserActionExportSuccess() throws Exception {
        String action = "export";
        String filePath = "C:\\Users\\user\\Documents\\export.csv";

        doNothing().when(userActionRandomService).exportUsers(filePath);

        mockMvc.perform(post("/user-action")
                        .param("action", action)
                        .param("filePath", filePath))
                .andExpect(status().isOk())
                .andExpect(content().string("Действие успешно выполнено."));

        verify(userActionRandomService, times(1))
                .exportUsers(filePath);
    }

    @Test
    public void testHandleUserActionMissingParamsThrowsException() throws Exception {
        String action = "load";

        doThrow(new UserActionException("Имя, фамилия и email должны быть указаны."))
                .when(userActionRandomService).loadUsers(isNull(), isNull(), isNull());

        mockMvc.perform(post("/user-action")
                        .param("action", action))
                .andExpect(status().isBadRequest());

        verify(userActionRandomService, times(1))
                .loadUsers(isNull(), isNull(), isNull());
    }
}
