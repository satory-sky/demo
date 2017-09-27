package au.com.serenity.ms.controller;

import au.com.serenity.ms.dto.PermissionRequestDto;
import au.com.serenity.ms.jsonapi.JsonApiObject;
import au.com.serenity.ms.exception.ApiException;
import au.com.serenity.ms.service.PermissionService;
import au.com.serenity.ms.validator.PermissionRequestDtoValidator;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PermissionController {
    private Logger log = LoggerFactory.getLogger(getClass());

    private final Map<String, AuthorizationScope[]> methodAuths;

    @Autowired
    private PermissionService permissionService;
    @Autowired
    private PermissionRequestDtoValidator permissionRequestDtoValidator;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        if (PermissionRequestDto.class.isInstance(binder.getTarget())) {
            binder.addValidators(permissionRequestDtoValidator);
        }
    }

    public PermissionController() {
        log.info("Starting PermissionController");

        methodAuths = new HashMap<String, AuthorizationScope[]>();
        for (Method method : PermissionController.class.getMethods()) {
            ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
            if (apiOperation != null && apiOperation.authorizations() != null) {
                Authorization[] auths = apiOperation.authorizations();
                for (Authorization auth : auths) {
                    if (auth.value().equals("serenity_auth") && auth.scopes() != null) {
                        log.info("Adding scopes for method " + method.getName() + " scopes: " + Arrays.toString(auth.scopes()));
                        methodAuths.put(method.getName(), auth.scopes());
                    }
                }
            }
        }
    }

    @RequestMapping(
        value = "/permissions",
        method = RequestMethod.POST,
        produces = {"application/json"})
    @ApiOperation(
        value = "Read Permissions",
        tags = {"permission"},
        notes = "Read Permissions",
        response = JsonApiObject.class,
        authorizations = {
            @Authorization(value = "serenity_auth", scopes = {
                @AuthorizationScope(scope = "permission", description = "Security Permissions"),
                @AuthorizationScope(scope = "permission:read", description = "Read Permissions")})},
        extensions = {
            @Extension(name = "x-sla", properties = {
                @ExtensionProperty(name = "availability", value = "85"),
                @ExtensionProperty(name = "responseTime", value = "100"),
                @ExtensionProperty(name = "requestsPerSecond", value = "50")
            })
        }
    )
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Invalid input", response = ApiException.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ApiException.class),
        @ApiResponse(code = 404, message = "Not Found", response = ApiException.class),
        @ApiResponse(code = 403, message = "Access Denied", response = ApiException.class),
        @ApiResponse(code = 500, message = "Server Error", response = ApiException.class)})
    public ResponseEntity<?> getPermissions(
        @RequestHeader(value = "X-AuthToken") String xAuthToken,
        @RequestBody @Validated PermissionRequestDto permissionRequestDto) {
        log.info(">>getPermissions (), ()", xAuthToken, permissionRequestDto);

        List<String> permissions = permissionService.getPermissions(permissionRequestDto);

        log.info("<<getPermissions");
        return new ResponseEntity<>(permissions, HttpStatus.OK);
    }
}


