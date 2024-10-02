package com.naveen.rentread.controller;

import com.naveen.rentread.domain.Role;
import com.naveen.rentread.model.UserRegisterRequest;
import com.naveen.rentread.model.UserResponse;
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
    public ResponseEntity<UserResponse> registerUser(@RequestBody UserRegisterRequest user){
        return ResponseEntity.ok().body(service.registerUser(user, Role.USER));
    }

    @PostMapping("/register/admin")
    public ResponseEntity<UserResponse> registerAdmin(@RequestBody UserRegisterRequest user){
        return ResponseEntity.ok().body(service.registerUser(user, Role.ADMIN));
    }

}
