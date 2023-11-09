package com.auth.controller;

import com.auth.model.UserEntity;
import com.auth.model.dto.CreateUserDto;
import com.auth.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final UserService service;

    @PostMapping({"/create"})
    public ResponseEntity<UserEntity> createUser(@RequestBody CreateUserDto dto) {
        return ResponseEntity.ok(this.service.createUser(dto));
    }

    public AuthController(final UserService service) {
        this.service = service;
    }
}
