package com.example.randomuser.controller;

import com.example.randomuser.service.UserExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserExportController {

    @Autowired
    private UserExportService userExportService;

    @GetMapping("/export/csv")
    public ResponseEntity<String> exportToCSV(@RequestParam String filePath) {
        userExportService.exportToCSV(filePath);
        return ResponseEntity.ok("Пользователи успешно выгружены в " + filePath);
        }
    }

