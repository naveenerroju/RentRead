package com.naveen.rentread.controller;

import com.naveen.rentread.domain.Role;
import com.naveen.rentread.model.UserRegisterRequest;
import com.naveen.rentread.model.UserResponse;
import com.naveen.rentread.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/unauth")
@Validated
public class AuthController {

    @Autowired
    private UserService service;

    @PostMapping("/register/user")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody UserRegisterRequest user){
        return ResponseEntity.ok().body(service.registerUser(user, Role.USER));
    }

    @PostMapping("/register/admin")
    public ResponseEntity<UserResponse> registerAdmin(@Valid @RequestBody UserRegisterRequest user){
        return ResponseEntity.ok().body(service.registerUser(user, Role.ADMIN));
    }

}
