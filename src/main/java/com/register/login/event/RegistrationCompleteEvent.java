package com.register.login.event;

import com.register.login.domain.entites.User;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent {

  private User user;
  private String applicationUrl;
  private LocalDateTime localDateTime = LocalDateTime.now();

  public RegistrationCompleteEvent(User user, String applicationUrl) {
    super(user);
    this.user = user;
    this.applicationUrl = applicationUrl;
  }
}
