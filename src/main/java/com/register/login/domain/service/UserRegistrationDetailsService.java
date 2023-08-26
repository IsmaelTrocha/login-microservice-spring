package com.register.login.domain.service;

import com.register.login.config.security.UserRegistrationDetails;
import com.register.login.infrastructure.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserRegistrationDetailsService implements UserDetailsService {

  private final UserRepository userRepository;


  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return userRepository.findByEmail(email)
        .map(UserRegistrationDetails::new)
        .orElseThrow(() -> new UsernameNotFoundException(
            "The User with email " + email + " was not found."));
  }
}
