package com.register.login.event.listener;

import com.register.login.domain.entites.User;
import com.register.login.domain.service.UserService;
import com.register.login.event.RegistrationCompleteEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@AllArgsConstructor
public class RegistrationCompleteEventListener implements
        ApplicationListener<RegistrationCompleteEvent> {

    private final UserService userService;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        //1. Get the new registered user
        User theUser = event.getUser();

        //2. Create a verification token for user
        String verificationToken = UUID.randomUUID().toString();

        //3. Save the verification token for the user.

        userService.saveUserVerificationToken(theUser, verificationToken);

        //4. Build the verification url to be sent to the user
        String url = event.getApplicationUrl() + "/register/verifyEmail?token=" + verificationToken;

        //5. Send the email

        log.info("Click the link below to complete your registration : {}", url);
    }
}
