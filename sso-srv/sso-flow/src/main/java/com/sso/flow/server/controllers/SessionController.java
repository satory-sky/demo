package com.sso.flow.server.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Original Author: Alexander Kontarero
 * @version 9/1/2014
 * @see © 2014 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
@Controller
public class SessionController {
    private final Logger log = LoggerFactory.getLogger(getClass());

//    http://localhost:8080/sso-flow/
    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String getHomePage(
        HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        log.debug(">>getHomePage {}, {}", request, response);

        log.debug("<<getHomePage");
        return "index";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/notFound")
    public String getNotFound(
        HttpServletRequest request, HttpServletResponse response) {
        log.debug(">>getNotFound {}, {}", request, response);

        return "not_found_error";
    }

//    @RequestMapping(method = RequestMethod.GET, value = "/session")
//    @ResponseBody
//    public ResponsePackageDto getSession(HttpServletRequest request, HttpServletResponse response,
//        @RequestParam(required = true) String auth){
//        log.debug(">>getLoginSuccess {}, {}", request, response);
//
//        ErrorCode errorCode = ErrorCode.INTERNAL_ERROR;
//        if(auth != null && auth.equals("success")){
//            errorCode = ErrorCode.SUCCESS;
//        } else if (auth != null && auth.equals("failed")){
//            errorCode = ErrorCode.INVALID_CREDENTIALS_ERROR;
//        }
//
//        log.debug("<<getLoginSuccess");
//        return new ResponsePackageDto(errorCode);
//    }

//    @RequestMapping(method = RequestMethod.GET, value = "/logout/success")
//    @ResponseBody
//    public String getLogoutSuccess(HttpServletRequest request, HttpServletResponse response){
//        log.debug(">>getLogoutSuccess {}, {}", request, response);
//
//        return "/logout/success";
//    }
}
