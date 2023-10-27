package com.register.login.config.jwt;

import com.register.login.domain.service.UserRegistrationDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  @Autowired
  private UserRegistrationDetailsService userRegistrationDetailsService;
  @Autowired
  private JwtUtils jwtUtils;
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain)
      throws ServletException, IOException {
    String jwtToken = extractJwtToken(request);

    if (jwtToken != null) {
      try {
        String username = jwtUtils.extractUsername(jwtToken);
        UserDetails userDetails = loadUserByUsername(username);

        if (jwtUtils.validateToken(jwtToken, userDetails)) {
          Authentication authentication = createAuthenticationToken(userDetails, request);
          SecurityContextHolder.getContext().setAuthentication(authentication);
        }
      } catch (ExpiredJwtException e) {
        handleExpiredTokenException(jwtToken);
      } catch (UsernameNotFoundException e) {
        // Handle the case when the user is not found.
      }
    }

    filterChain.doFilter(request, response);
  }

  private String extractJwtToken(HttpServletRequest request) {
    String requestTokenHeader = request.getHeader("Authorization");
    if (!StringUtils.isEmpty(requestTokenHeader) && requestTokenHeader.startsWith("Bearer ")) {
      return requestTokenHeader.substring(7);
    }
    return null;
  }

  private UserDetails loadUserByUsername(String username) {
    try {
      return userRegistrationDetailsService.loadUserByUsername(username);
    } catch (UsernameNotFoundException e) {
      // Handle the case when the user is not found.
      throw e;
    }
  }

  private Authentication createAuthenticationToken(UserDetails userDetails,
      HttpServletRequest request) {
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
        userDetails, null, userDetails.getAuthorities());
    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    return authenticationToken;
  }

  private void handleExpiredTokenException(String jwtToken) {
    log.info("The token has expired: {}", jwtToken);
    throw new RuntimeException("Token has expired");
  }
}
