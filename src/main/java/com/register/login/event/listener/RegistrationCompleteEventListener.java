package com.register.login.event.listener;

import com.register.login.domain.entites.User;
import com.register.login.domain.service.UserService;
import com.register.login.event.RegistrationCompleteEvent;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
@Slf4j
@Component
@RequiredArgsConstructor
public class RegistrationCompleteEventListener implements
    ApplicationListener<RegistrationCompleteEvent> {

  private final UserService userService;
  private final JavaMailSender mailSender;
  private User theUser;


  @Override
  public void onApplicationEvent(RegistrationCompleteEvent event) {
    //1. Get the new registered user
    theUser = event.getUser();

    //2. Create a verification token for user
    String verificationToken = UUID.randomUUID().toString();

    //3. Save the verification token for the user.

    userService.saveUserVerificationToken(theUser, verificationToken);

    //4. Build the verification url to be sent to the user
    String url = event.getApplicationUrl() + "/register/verifyEmail?token=" + verificationToken;

    //5. Send the email

    try {
      sendEmailValidation(url);
    } catch (MessagingException | UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }

    log.info("Click the link below to complete your registration : {}", url);
  }

  public void sendEmailValidation(String url)
      throws MessagingException, UnsupportedEncodingException {

    String subject = "Email Verification";
    String senderName = "Liquor Golden Registration Portal Service.";
    String mailContent = "<p> Hi, " + theUser.getFirstName() + ", </p>" +
        "<p>Thank you for registering with us," + "" +
        "Please, follow the link below to complete your registration.</p>" +
        "<a href=\"" + url + "\">Verify your email to activate your account</a>" +
        "<p> Thank you <br> Liquor Golden Registration Portal Service";
    MimeMessage message = mailSender.createMimeMessage();
    var messageHelper = new MimeMessageHelper(message);
    messageHelper.setFrom("ismaeltrocha@gmail.com", senderName);
    messageHelper.setTo(theUser.getEmail());
    messageHelper.setSubject(subject);
    messageHelper.setText(mailContent, true);
    mailSender.send(message);

  }
}
