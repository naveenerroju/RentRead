package com.naveen.rentread.controller;

import com.naveen.rentread.domain.Role;
import com.naveen.rentread.domain.User;
import com.naveen.rentread.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;

@RestController
@RequestMapping("/unauth")
public class AuthController {

    @Autowired
    private UserService service;

    @PostMapping("/register/user")
    public ResponseEntity<User> registerUser(@RequestBody User user){
        user.setRoles(new HashSet<>());
        user.getRoles().add(Role.USER);
        return ResponseEntity.ok().body(service.registerUser(user));
    }

    @PostMapping("/register/admin")
    public ResponseEntity<User> registerAdmin(@RequestBody User user){
        user.getRoles().add(Role.ADMIN);
        return ResponseEntity.ok().body(service.registerUser(user));
    }

}
