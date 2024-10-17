package com.example.randomuser.handler;

import com.example.randomuser.exception.UserActionException;
import com.example.randomuser.exception.UserExportException;
import com.example.randomuser.exception.UserServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserServiceException.class)
    public ResponseEntity<String> handleUserServiceException(UserServiceException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserExportException.class)
    public ResponseEntity<String> handleUserExportException(UserExportException ex) {
        return new ResponseEntity<>("Ошибка экспорта пользователей: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex, WebRequest request) {
        return new ResponseEntity<>("Произошла ошибка на сервере", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserActionException.class)
    public ResponseEntity<String> handleUserActionException(UserActionException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
