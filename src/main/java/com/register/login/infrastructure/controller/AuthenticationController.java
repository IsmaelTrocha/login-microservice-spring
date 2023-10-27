package com.register.login.infrastructure.controller;

import com.register.login.config.jwt.JwtUtils;
import com.register.login.domain.entites.JwtRequest;
import com.register.login.domain.entites.JwtResponse;
import com.register.login.domain.service.UserRegistrationDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/login")
public class AuthenticationController {

  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private UserRegistrationDetailsService userRegistrationDetailsService;

  @Autowired
  private JwtUtils jwtUtils;

  public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) {

    try {
      authenticate(jwtRequest);
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("User was not authenticated");
    }

    UserDetails userDetails = userRegistrationDetailsService.loadUserByUsername(
        jwtRequest.getEmail());
    String token = jwtUtils.generateToken(userDetails);
    return new ResponseEntity<>(new JwtResponse(token), HttpStatus.OK);
  }

  private void authenticate(@RequestBody JwtRequest jwtRequest) throws Exception {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(jwtRequest.getEmail(), jwtRequest.getPassword()));
    } catch (DisabledException e) {
      throw new Exception("User doesn't enabled yet: " + e.getMessage());
    } catch (BadCredentialsException badCredentialsException) {
      throw new Exception("Invalid Credential: " + badCredentialsException.getMessage());
    }
  }

}
