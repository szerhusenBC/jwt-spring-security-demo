package org.zerhusen;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JwtDemoApplicationTest {

    @Test
    public void contextLoads() {
        // just test if the application context loads
       System.out.println("Hello");
    }
}
