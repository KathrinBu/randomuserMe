package com.example.randomuser.service;

import com.example.randomuser.domain.entity.User;
import com.example.randomuser.exception.UserExportException;
import com.example.randomuser.repository.UserRepository;
import com.opencsv.CSVWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
public class UserExportService {
    private static final Logger logger = LoggerFactory.getLogger(UserExportService.class);

    @Value("${export.allowed-directory:C:\\Users\\user\\Documents\\}")
    private String allowedDirectory;

    @Autowired
    private UserRepository userRepository;

    public void exportToCSV(String filePath) throws UserExportException {
        logger.info("Начинается экспорт в файл: {}", filePath);
        validateFilePath(filePath);

        File file = new File(filePath);
        validateFilePermissions(file);

        try {
            List<User> users = fetchUsers();;
            writeUsersToCSV(filePath, users);
            logger.info("Экспорт успешно завершен в файл: " + filePath);
        } catch (IOException e) {
            logger.error("Ошибка ввода-вывода при экспорте", e);
            throw new UserExportException("Ошибка при экспорте пользователей в файл " + filePath, e);
        } catch (RuntimeException e) {
            logger.error("Ошибка работы с базой данных", e);
            throw new UserExportException("Ошибка при работе с базой данных", e);
        }
    }

    private void validateFilePath(String filePath) throws UserExportException {
        if (!filePath.startsWith(allowedDirectory)) {
            throw new UserExportException("Нельзя сохранять файл вне разрешенной директории: " + allowedDirectory);
        }
    }

    private void validateFilePermissions(File file) throws UserExportException {
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            throw new UserExportException("Директория не существует: " + parentDir.getAbsolutePath());
        }

        if (parentDir != null && !parentDir.canWrite()) {
            throw new UserExportException("Нет прав на запись в директорию: " + parentDir.getAbsolutePath());
        }
    }

    private List<User> fetchUsers() {
        return userRepository.findAll(PageRequest.of(0, 1000)).getContent();
    }

    void writeUsersToCSV(String filePath, List<User> users) throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            writer.writeNext(new String[]{"ID", "First Name", "Last Name", "Email"});
            for (User user : users) {
                writer.writeNext(new String[]{
                        String.valueOf(user.getId()),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail()
                });
            }
        }
    }
}

