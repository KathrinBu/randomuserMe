package com.example.randomuser.service;

import com.example.randomuser.domain.entity.User;
import com.example.randomuser.exception.UserServiceException;
import com.example.randomuser.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveUserSuccess() {
        String firstName = "Ivan";
        String lastName = "Petrov";
        String email = "john.petrovich@example.com";

        userService.saveUser(firstName, lastName, email);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testSaveUserThrowsException() {
        String firstName = "Marya";
        String lastName = "Petrova";
        String email = "marya.petrovich@example.com";

        doThrow(new RuntimeException("Database error")).when(userRepository).save(any(User.class));

        assertThrows(UserServiceException.class, () -> {
            userService.saveUser(firstName, lastName, email);
        });
    }
}
