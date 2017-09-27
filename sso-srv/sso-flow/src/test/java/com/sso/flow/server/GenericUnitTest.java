package com.sso.flow.server;


import com.sso.flow.server.config.spring.AppConfig;
import com.sso.flow.server.config.spring.PersistentConfig;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by: Alexander Kontarero
 * Date: 2/20/2015
 * Time: 10:16 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(
    classes = {AppConfig.class, PersistentConfig.class}, loader = AnnotationConfigWebContextLoader.class)
// rollback is true by default for test
//@Rollback(false)
@Transactional
//@ActiveProfiles("dev")
public abstract class GenericUnitTest {
}
