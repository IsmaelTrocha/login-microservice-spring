package com.register.login.infrastructure.controller;

import com.register.login.domain.entites.User;
import com.register.login.domain.registration.RegistrationRequest;
import com.register.login.event.RegistrationCompleteEvent;
import com.register.login.infrastructure.adapter.UserServiceAdapter;
import com.register.login.infrastructure.repository.VerificationTokenRepository;
import com.register.login.infrastructure.repository.token.VerificationToken;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/register")
@AllArgsConstructor
@Slf4j
public class RegistrationController {

    private final UserServiceAdapter userService;
    private final ApplicationEventPublisher publisher;
    private final VerificationTokenRepository verificationTokenRepository;

    @PostMapping
    public ResponseEntity<String> registerUser(@RequestBody RegistrationRequest registrationRequest, final HttpServletRequest servlet) {
        User user = userService.registerUser(registrationRequest);
        publisher.publishEvent(new RegistrationCompleteEvent(user, applicationUrl(servlet)));
        return new ResponseEntity<>("Success! Please, check your email for to complete the registration", HttpStatus.OK);
    }

    @GetMapping(value = "/verifyEmail")
    public String verifyEmail(@RequestParam("token") String token) {
        VerificationToken theToken = verificationTokenRepository.findByToken(token);
        if (theToken.getUser().getEnabled()) {
            return "This account has already been verified, please, login.";
        }
        String verificationResult = userService.validateToken(token);
        if (verificationResult.equalsIgnoreCase("Token has been validate Successfully!!")) {
            return "Email verified successfully. Now you can login to your account";
        }
        return "Invalid verification link";
    }


    public String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":"
                + request.getServerPort() + request.getContextPath();
    }
}