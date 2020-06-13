package com.roman.jwtskeleton.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/test")
    public String test() {
        return "¡Hola mundo!";
    }

}
