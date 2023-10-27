package com.register.login.infrastructure.controller;

import com.register.login.domain.entites.User;
import com.register.login.infrastructure.adapter.UserServiceAdapter;
import com.register.login.infrastructure.repository.UserRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/users")
@AllArgsConstructor
public class UserController {

  private final UserServiceAdapter userServiceAdapter;
  private final UserRepository userRepository;

  @GetMapping(path = "/getAll")
  public ResponseEntity<List<User>> getAllUsers() {
    return new ResponseEntity<>(userServiceAdapter.getUser(), HttpStatus.FOUND);
  }

  @GetMapping(path = "/{id}")
  public ResponseEntity<User> getUserById(@PathVariable("id") Long id){
    return new ResponseEntity<>(userServiceAdapter.findById(id),HttpStatus.OK);
  }

  @PostMapping(
      value = "customerId",
      consumes = MediaType.MULTIPART_FORM_DATA_VALUE
  )
  public void updateUserProfileImage(
      @PathVariable("id") Long customerId,
      @RequestParam("file") MultipartFile file) {
    userRepository.updateCustomerProfileImageId(customerId, file.getOriginalFilename());
  }
}
