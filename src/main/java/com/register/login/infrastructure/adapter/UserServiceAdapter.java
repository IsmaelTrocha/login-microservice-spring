package com.register.login.infrastructure.adapter;

import com.register.login.domain.entites.User;
import com.register.login.domain.registration.RegistrationRequest;
import com.register.login.domain.service.UserService;
import com.register.login.infrastructure.mapper.UserMapper;
import com.register.login.infrastructure.repository.UserRepository;
import com.register.login.infrastructure.repository.VerificationTokenRepository;
import com.register.login.infrastructure.repository.token.VerificationToken;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceAdapter implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository verificationTokenRepository;

    @Override
    public List<User> getUser() {
        return userRepository.findAll();
    }

    @Override
    public User registerUser(RegistrationRequest registrationRequest) {
        Optional<User> optionalUser = this.userRepository.findByEmail(registrationRequest.getEmail());

        if (optionalUser.isPresent()) {
            throw new RuntimeException("User with email " + registrationRequest.getEmail() + " already exists");
        }
        registrationRequest.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        return userRepository.save(userMapper.toEntity(registrationRequest));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void saveUserVerificationToken(User theUser, String verificationToken) {
        VerificationToken verification = new VerificationToken(verificationToken, theUser);
        verificationTokenRepository.save(verification);

    }

    @Override
    public String validateToken(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);

        if (token == null) {
            return "Invalid verification Token!";
        }

        User user = verificationToken.getUser();
        if (verificationToken.getExpirationToken().isBefore(LocalDateTime.now())) {
            verificationTokenRepository.delete(verificationToken);
            return "Token has expired. :(";
        }
        user.setEnabled(Boolean.TRUE);
        userRepository.save(user);

        return "Token has been validate Successfully!!";
    }
}
