package com.register.login.domain.entites;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor

public class JwtRequest {

  private String email;
  private String password;
  
}
