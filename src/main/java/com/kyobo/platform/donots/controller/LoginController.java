package com.kyobo.platform.donots.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class LoginController {
    @GetMapping({"/", ""})
    public String loginForm(){
        return "loginForm";
    }
}