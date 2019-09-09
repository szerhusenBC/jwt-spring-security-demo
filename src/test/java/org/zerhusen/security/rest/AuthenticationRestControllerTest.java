package org.zerhusen.security.rest;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.zerhusen.util.AbstractRestControllerTest;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthenticationRestControllerTest extends AbstractRestControllerTest {

   @Test
   public void successfulAuthenticationWithUser() throws Exception {
      getMockMvc().perform(post("/api/authenticate")
         .contentType(MediaType.APPLICATION_JSON)
         .content("{\"password\": \"password\", \"username\": \"user\"}"))
         .andExpect(status().isOk())
         .andExpect(content().string(containsString("id_token")));
   }

   @Test
   public void successfulAuthenticationWithAdmin() throws Exception {
      getMockMvc().perform(post("/api/authenticate")
         .contentType(MediaType.APPLICATION_JSON)
         .content("{\"password\": \"admin\", \"username\": \"admin\"}"))
         .andExpect(status().isOk())
         .andExpect(content().string(containsString("id_token")));
   }

   @Test
   public void unsuccessfulAuthenticationWithDisabled() throws Exception {
      getMockMvc().perform(post("/api/authenticate")
         .contentType(MediaType.APPLICATION_JSON)
         .content("{\"password\": \"password\", \"username\": \"disabled\"}"))
         .andExpect(status().isUnauthorized())
         .andExpect(content().string(not(containsString("id_token"))));
   }

   @Test
   public void unsuccessfulAuthenticationWithWrongPassword() throws Exception {
      getMockMvc().perform(post("/api/authenticate")
         .contentType(MediaType.APPLICATION_JSON)
         .content("{\"password\": \"wrong\", \"username\": \"user\"}"))
         .andExpect(status().isUnauthorized())
         .andExpect(content().string(not(containsString("id_token"))));
   }

   @Test
   public void unsuccessfulAuthenticationWithNotExistingUser() throws Exception {
      getMockMvc().perform(post("/api/authenticate")
         .contentType(MediaType.APPLICATION_JSON)
         .content("{\"password\": \"password\", \"username\": \"not_existing\"}"))
         .andExpect(status().isUnauthorized())
         .andExpect(content().string(not(containsString("id_token"))));
   }

}
