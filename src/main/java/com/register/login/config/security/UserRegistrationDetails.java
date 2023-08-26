package com.register.login.config.security;

import com.register.login.domain.entites.User;
import java.util.Collection;
import java.util.List;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
public class UserRegistrationDetails implements UserDetails {

  private String username;
  private String password;
  private Boolean enabled;
  private String role;
  private List<GrantedAuthority> authorities;

  public UserRegistrationDetails(User user) {
    this.username = user.getFirstName();
    this.password = user.getPassword();
    this.enabled = user.getEnabled();
    this.role = user.getRole();
    this.authorities = List.of(new SimpleGrantedAuthority(user.getRole()));
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }
}
