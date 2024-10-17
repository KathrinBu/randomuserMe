package com.example.randomuser.controller;

import com.example.randomuser.exception.UserExportException;
import com.example.randomuser.service.UserExportService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserExportController.class)
public class UserExportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserExportService userExportService;

    @Test
    public void testExportToCSVSuccess() throws Exception {
        String filePath = "C:/Users/user/Documents/export.csv";

        doNothing().when(userExportService).exportToCSV(filePath);

        mockMvc.perform(get("/users/export/csv")
                        .param("filePath", filePath))
                .andExpect((ResultMatcher) status().isOk())
                .andExpect(content().string("Пользователи успешно выгружены в " + filePath));

        verify(userExportService, times(1)).exportToCSV(filePath);
    }

    @Test
    public void testExportToCSVFailure() throws Exception {
        String filePath = "C:/Users/user/Documents/export.csv";

        doThrow(new UserExportException("Ошибка экспорта")).when(userExportService).exportToCSV(filePath);

        mockMvc.perform(get("/users/export/csv")
                        .param("filePath", filePath))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Ошибка экспорта пользователей: Ошибка экспорта"));

        verify(userExportService, times(1)).exportToCSV(filePath);
    }
}
