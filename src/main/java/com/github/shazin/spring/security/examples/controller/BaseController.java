package com.github.shazin.spring.security.examples.controller;

import org.springframework.security.web.WebAttributes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * Created by shazi on 4/26/2016.
 */
@RestController
public class BaseController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "Hello, World! at "+ LocalDateTime.now();
    }

    @RequestMapping(value = "/success")
    public String success() {
        return "Success at "+LocalDateTime.now();
    }

    @RequestMapping(value = "/failure")
    public String failure(HttpServletRequest request) {
        return "Failure at "+LocalDateTime.now()+" caused by "+request.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

}
