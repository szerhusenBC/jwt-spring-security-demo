package org.zerhusen.security.rest.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO for storing a user's credentials.
 */
public class LoginDto {

   @NotNull
   @Size(min = 1, max = 50)
   private String username;

   @NotNull
   @Size(min = 4, max = 100)
   private String password;

   private Boolean rememberMe;

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public Boolean isRememberMe() {
      return rememberMe;
   }

   public void setRememberMe(Boolean rememberMe) {
      this.rememberMe = rememberMe;
   }

   @Override
   public String toString() {
      return "LoginVM{" +
         "username='" + username + '\'' +
         ", rememberMe=" + rememberMe +
         '}';
   }
}
