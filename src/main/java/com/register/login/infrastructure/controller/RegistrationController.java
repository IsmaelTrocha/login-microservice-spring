package com.register.login.infrastructure.controller;

import com.register.login.domain.entites.User;
import com.register.login.domain.registration.RegistrationRequest;
import com.register.login.domain.service.UserService;
import com.register.login.event.RegistrationCompleteEvent;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/register")
@AllArgsConstructor
public class RegistrationController {

    private final UserService userService;
    private final ApplicationEventPublisher publisher;

    @PostMapping
    public ResponseEntity<String> registerUser(@RequestBody RegistrationRequest registrationRequest, final HttpServletRequest servlet) {
        User user = userService.registerUser(registrationRequest);
        publisher.publishEvent(new RegistrationCompleteEvent(user, applicationUrl(servlet)));
        return new ResponseEntity<>("Success! Please, check your email for to complete the registration", HttpStatus.OK);
    }

    public String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }


}
