package com.sso.idm.server.controllers.api;

import com.sso.idm.server.controllers.UserController;
import com.sso.idm.server.controllers.RoleController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

public class SrvAPI {
    // V1
    @Controller
    @RequestMapping("/api/V1/user") public static class V1_user extends UserController {}
    @Controller
    @RequestMapping("/api/V1/role") public static class V1_role extends RoleController {}
}
