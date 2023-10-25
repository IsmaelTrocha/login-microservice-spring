package com.register.login.infrastructure.controller;

import com.register.login.domain.entites.User;
import com.register.login.infrastructure.adapter.UserServiceAdapter;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/users")
@AllArgsConstructor
public class UserController {

  private final UserServiceAdapter userServiceAdapter;

  @GetMapping(path = "/getAll")
  public ResponseEntity<List<User>> getAllUsers() {
    List<User> usuarios = userServiceAdapter.getUser();
    for (User u : usuarios) {
      BCryptPasswordEncoder password = new BCryptPasswordEncoder();
      String passwordEncoded = password.encode("1234");
      u.setPassword(passwordEncoded);
      boolean passwordMatch = password.matches("1234", u.getPassword());
      u.setMatch(passwordMatch);
    }
    return new ResponseEntity<>(usuarios, HttpStatus.FOUND);
  }
}
