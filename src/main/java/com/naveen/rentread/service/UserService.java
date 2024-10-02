package com.naveen.rentread.service;

import com.naveen.rentread.config.SecurityConfig;
import com.naveen.rentread.domain.User;
import com.naveen.rentread.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private SecurityConfig securityConfig;


    public User registerUser(User user){
        String encryptedPassword =  securityConfig.passwordEncoder().encode(user.getPassword());
        user.setPassword(encryptedPassword);
        return repository.save(user);

    }
}
