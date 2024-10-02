package com.naveen.rentread.service;

import com.naveen.rentread.config.SecurityConfig;
import com.naveen.rentread.domain.Role;
import com.naveen.rentread.domain.User;
import com.naveen.rentread.model.UserRegisterRequest;
import com.naveen.rentread.model.UserResponse;
import com.naveen.rentread.repos.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private SecurityConfig securityConfig;

    public UserResponse registerUser(UserRegisterRequest user, Role role){
        ModelMapper modelMapper = new ModelMapper();

        User entity = modelMapper.map(user, User.class);
        entity.setRoles(new HashSet<>());
        entity.getRoles().add(role);
        String encryptedPassword =  securityConfig.passwordEncoder().encode(user.getPassword());
        entity.setPassword(encryptedPassword);
        User response = repository.save(entity);
        return modelMapper.map(response, UserResponse.class);
    }
}
