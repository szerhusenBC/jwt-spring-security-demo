package org.zerhusen.security.rest;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserRestControllerTest {

   private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

   @Autowired
   private MockMvc mockMvc;

   @Test
   public void getActualUserForUserWithToken() throws Exception {
      final String token = getTokenForLogin("user", "password");

      mockMvc.perform(get("/api/user")
         .contentType(MediaType.APPLICATION_JSON)
         .header("Authorization", "Bearer " + token))
         .andExpect(status().isOk())
         .andExpect(content().json(
            "{\n" +
               "  \"username\" : \"user\",\n" +
               "  \"firstname\" : \"user\",\n" +
               "  \"lastname\" : \"user\",\n" +
               "  \"email\" : \"enabled@user.com\",\n" +
               "  \"authorities\" : [ {\n" +
               "    \"name\" : \"ROLE_USER\"\n" +
               "  } ]\n" +
               "}"
         ));
   }

   @Test
   public void getActualUserForUserWithoutToken() throws Exception {
      mockMvc.perform(get("/api/user")
         .contentType(MediaType.APPLICATION_JSON))
         .andExpect(status().isUnauthorized());
   }

   private String getTokenForLogin(String username, String password) throws Exception {
      String content = mockMvc.perform(post("/api/authenticate")
         .contentType(MediaType.APPLICATION_JSON)
         .content("{\"password\": \"" + password + "\", \"username\": \"" + username + "\"}"))
         .andReturn()
         .getResponse()
         .getContentAsString();
      AuthenticationResponse authResponse = OBJECT_MAPPER.readValue(content, AuthenticationResponse.class);

      return authResponse.getIdToken();
   }

   private static class AuthenticationResponse {

      @JsonAlias("id_token")
      private String idToken;

      public void setIdToken(String idToken) {
         this.idToken = idToken;
      }

      public String getIdToken() {
         return idToken;
      }
   }
}
