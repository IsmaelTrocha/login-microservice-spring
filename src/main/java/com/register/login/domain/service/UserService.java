package com.register.login.domain.service;

import com.register.login.domain.entites.User;
import com.register.login.domain.registration.RegistrationRequest;

import java.util.List;
import java.util.Optional;

public interface UserService {

  List<User> getUser();

  User registerUser(RegistrationRequest registrationRequest);

  Optional<User> findByEmail(String email);

  void saveUserVerificationToken(User theUser, String verificationToken);

  String validateToken(String token);

}
