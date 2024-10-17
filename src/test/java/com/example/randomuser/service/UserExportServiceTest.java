package com.example.randomuser.service;
import com.example.randomuser.domain.entity.User;
import com.example.randomuser.exception.UserExportException;
import com.example.randomuser.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.util.ReflectionTestUtils;
import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class UserExportServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserExportService userExportService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(userExportService, "allowedDirectory", "C:\\Users\\user\\Documents\\");
    }

    @Test
    public void testExportToCSVSuccess() throws Exception {
        String filePath = "C:\\Users\\user\\Documents\\export.csv";
        File mockFile = mock(File.class);

        when(mockFile.getParentFile()).thenReturn(mock(File.class));

        when(mockFile.getParentFile().exists()).thenReturn(true);
        when(mockFile.getParentFile().canWrite()).thenReturn(true);

        when(userRepository.findAll(any(PageRequest.class))).thenReturn(new PageImpl<>(List.of(mock(User.class), mock(User.class))));

        when(mockFile.setWritable(true)).thenReturn(true);

        userExportService.exportToCSV(filePath);

        verify(userRepository, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    public void testExportToCSVInvalidDirectory() {
        String invalidFilePath = "D:\\export.csv";

        assertThrows(UserExportException.class, () -> {
            userExportService.exportToCSV(invalidFilePath);
        });
    }

    @Test
    public void testExportToCSVFileNotWritable() {
        String filePath = "C:\\Users\\user\\Documents\\export.csv";
        File mockFile = mock(File.class);
        when(mockFile.getParentFile()).thenReturn(mock(File.class));
        when(mockFile.getParentFile().exists()).thenReturn(true);
        when(mockFile.getParentFile().canWrite()).thenReturn(false);

        assertThrows(UserExportException.class, () -> {
            userExportService.exportToCSV(filePath);
        });
    }

    @Test
    public void testExportToCSVRuntimeException() {
        String filePath = "C:\\Users\\user\\Documents\\export.csv";

        when(userRepository.findAll((Example<User>) any())).thenThrow(new RuntimeException("DB Error"));

        assertThrows(UserExportException.class, () -> {
            userExportService.exportToCSV(filePath);
        });
    }
}