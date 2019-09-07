package org.zerhusen.security;

import org.junit.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class SecurityUtilsTest {

   @Test
   public void getCurrentUserLogin() {
      SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
      securityContext.setAuthentication(new UsernamePasswordAuthenticationToken("admin", "admin"));
      SecurityContextHolder.setContext(securityContext);

      Optional<String> login = SecurityUtils.getCurrentUserLogin();

      assertThat(login).contains("admin");
   }
}
