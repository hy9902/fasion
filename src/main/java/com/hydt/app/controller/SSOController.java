package com.hydt.app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("sso")
public class SSOController {

    @RequestMapping("/user")
    public Principal user(Principal principal) {
        return principal;
    }

    @
}
