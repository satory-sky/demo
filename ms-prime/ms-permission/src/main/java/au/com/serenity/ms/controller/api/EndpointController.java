package au.com.serenity.ms.controller.api;

import au.com.serenity.ms.controller.PermissionController;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

public class EndpointController {
    // V1
    @RestController
    @RequestMapping(value = "v1/security", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Api(value = "assertion")
    public static class V1_PermissionController extends PermissionController {}
}
