package com.register.login.infrastructure.controller;

import com.register.login.domain.entites.User;
import com.register.login.infrastructure.adapter.UserServiceAdapter;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
@AllArgsConstructor
public class UserController {

    private final UserServiceAdapter userServiceAdapter;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        return new ResponseEntity<>(userServiceAdapter.getUser(), HttpStatus.FOUND);
    }
}
