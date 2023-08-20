package com.register.login.domain.registration.token;


import com.register.login.domain.entites.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "VERIFICATION_TOKEN")
@AllArgsConstructor
@NoArgsConstructor
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


    public VerificationToken(String token, User user) {
        this.token = token;
        this.user = user;
        this.expirationToken = LocalDateTime.now().plusMinutes(15);
    }
}
