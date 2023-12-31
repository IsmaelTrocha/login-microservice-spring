package com.register.login.infrastructure.controller;

import com.register.login.domain.entites.User;
import com.register.login.infrastructure.adapter.UserServiceAdapter;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/users")
@AllArgsConstructor
public class UserController {

  private final UserServiceAdapter userServiceAdapter;

  @GetMapping(path = "/getAll")
  public ResponseEntity<List<User>> getAllUsers() {
    return new ResponseEntity<>(userServiceAdapter.getUser(), HttpStatus.FOUND);
  }

  @GetMapping(path = "/{id}")
  public ResponseEntity<User> getUserById(@PathVariable("id") Long id){
    return new ResponseEntity<>(userServiceAdapter.findById(id),HttpStatus.OK);
  }
}
