package au.com.serenity.ms.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes=SimpleBootCxfSystemTestApplication.class)
@WebIntegrationTest("server.port:8090")
public class ApplicationAuthSystemTests {
//    @Autowired
//    private IKeypadService iKeypadServiceSystemTestClient;

    @Test
    public void getCityForecastByZIP() {
/*        // Given
        ForecastRequest forecastRequest = generateDummyRequest();

        // When
        ForecastReturn forecastReturn = iKeypadService.getCityForecastByZIP(forecastRequest);

        // Then
        assertNotNull(forecastReturn);
        assertEquals(true, forecastReturn.isSuccess());
        assertEquals("Weimar", forecastReturn.getCity());
        assertEquals("22%", forecastReturn.getForecastResult().getForecast().get(0).getProbabilityOfPrecipiation().getDaytime());*/
    }


}