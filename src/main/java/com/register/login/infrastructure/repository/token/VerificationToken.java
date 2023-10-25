package com.register.login.infrastructure.repository.token;


import com.register.login.domain.entites.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "VERIFICATION_TOKEN")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class VerificationToken {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String token;
  @Column(name = "expiration_time")
  private LocalDateTime expirationToken;
  @OneToOne
  @JoinColumn(name = "user_id")
  private User user;
  @Column(name = "confirmation_time")
  private LocalDateTime confirmationToken;
  public VerificationToken(String token, User user) {
    this.token = token;
    this.user = user;
    this.expirationToken = LocalDateTime.now().plusMinutes(15);
  }
}
