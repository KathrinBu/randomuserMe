package com.example.randomuser.handler;

import com.example.randomuser.exception.UserActionException;
import com.example.randomuser.exception.UserExportException;
import com.example.randomuser.exception.UserServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class GlobalExceptionHandlerTest {
    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    public void testHandleUserServiceException() {
        UserServiceException exception = new UserServiceException("Ошибка в UserService");
        ResponseEntity<String> response = handler.handleUserServiceException(exception);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Ошибка в UserService", response.getBody());
    }

    @Test
    public void testHandleUserExportException() {
        UserExportException exception = new UserExportException("Ошибка экспорта");
        ResponseEntity<String> response = handler.handleUserExportException(exception);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Ошибка экспорта пользователей: Ошибка экспорта", response.getBody());
    }

    @Test
    public void testHandleGenericException() {
        Exception exception = new Exception("Generic exception");
        ResponseEntity<String> response = handler.handleGenericException(exception, null);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Произошла ошибка на сервере", response.getBody());
    }

    @Test
    public void testHandleUserActionException() {
        UserActionException exception = new UserActionException("Неверное действие");
        ResponseEntity<String> response = handler.handleUserActionException(exception);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Неверное действие", response.getBody());
    }
}
