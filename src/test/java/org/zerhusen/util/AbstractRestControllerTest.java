package org.zerhusen.util;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public abstract class AbstractRestControllerTest {

   @Autowired
   private MockMvc mockMvc;

   @Before
   public void setUp() {
      SecurityContextHolder.clearContext();
   }

   public MockMvc getMockMvc() {
      return mockMvc;
   }
}
