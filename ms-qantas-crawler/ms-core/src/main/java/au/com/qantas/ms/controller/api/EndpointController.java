package au.com.qantas.ms.controller.api;

import au.com.qantas.ms.controller.UrlTreeController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

public class EndpointController {
    private EndpointController() {
    }

    // V1
    @RestController
    @RequestMapping(value = "v1/crawler", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public static class V1UrlTreeController extends UrlTreeController {
    }
}
