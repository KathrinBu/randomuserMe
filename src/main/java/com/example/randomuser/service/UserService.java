package com.example.randomuser.service;

import com.example.randomuser.domain.entity.User;
import com.example.randomuser.exception.UserServiceException;
import com.example.randomuser.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void saveUser(String firstName, String lastName, String email) {
        try {
            User user = new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);

            userRepository.save(user);
        } catch (Exception e) {
            throw new UserServiceException("Ошибка при сохранении пользователя: " + firstName + " " + lastName, e);
        }
    }
}
