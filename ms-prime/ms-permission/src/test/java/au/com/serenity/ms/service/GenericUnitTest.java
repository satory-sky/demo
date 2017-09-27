package au.com.serenity.ms.service;

import au.com.serenity.ms.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
// rollback is true by default for test
//@Rollback(false)
//@ActiveProfiles("dev")
public class GenericUnitTest {

    @Test
    public void contextLoads() {
    }
}
