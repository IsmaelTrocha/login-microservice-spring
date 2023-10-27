package com.register.login.config.jwt;

import com.register.login.domain.service.UserRegistrationDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import java.io.IOException;
import java.util.Objects;
import javax.servlet.Filter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  @Autowired
  private UserRegistrationDetailsService userRegistrationDetailsService;
  @Autowired
  private JwtUtils jwtUtils;

  @Override
  protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request,
      jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain filterChain)
      throws jakarta.servlet.ServletException, IOException {
    String requestTokenHeader = request.getHeader("Authorization");
    String username = null;
    String jwtToken = null;

    if (!Objects.isNull(requestTokenHeader) && requestTokenHeader.startsWith("Bearer")) {
      jwtToken = requestTokenHeader.substring(7);

      try {
        username = this.jwtUtils.extractUsername(jwtToken);
      } catch (ExpiredJwtException e) {
        log.info("The token has expired {}", jwtToken);
        throw new RuntimeException(e.getCause());
      }
    }
    if (username != null && SecurityContextHolder.getContext().getAuthentication() != null) {
      UserDetails userDetails = this.userRegistrationDetailsService.loadUserByUsername(username);
      if (jwtUtils.validateToken(jwtToken, userDetails)) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationTokens = new UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.getAuthorities());
        usernamePasswordAuthenticationTokens.setDetails(
            new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationTokens);
      }
      filterChain.doFilter(request, response);
    }
  }
}
